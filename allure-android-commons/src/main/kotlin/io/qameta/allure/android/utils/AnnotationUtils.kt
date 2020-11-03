package io.qameta.allure.android.utils

import io.qameta.allure.android.annotations.LabelAnnotation
import io.qameta.allure.android.annotations.LinkAnnotation
import io.qameta.allure.android.model.Label
import io.qameta.allure.android.model.Link
import java.lang.reflect.AnnotatedElement
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.*
import kotlin.collections.HashSet

/**
 * Collection of utils used by Allure integration to extract meta information from
 * test cases via reflection.
 *
 */
object AnnotationUtils {
    private const val VALUE_METHOD_NAME = "value"
    /**
     * Returns links created from Allure meta annotations specified on annotated element.
     *
     * @param annotatedElement the element to search annotations on.
     * @return discovered links.
     */
    @JvmStatic
    fun getLinks(annotatedElement: AnnotatedElement): Set<Link> {
        return getLinks(*annotatedElement.declaredAnnotations)
    }

    /**
     * Shortcut for [getLinks].
     *
     * @param annotations annotations to analyse.
     * @return discovered links.
     */
    @JvmStatic
    fun getLinks(vararg annotations: Annotation): Set<Link> {
        return getLinks(annotations.toList())
    }

    /**
     * Returns links from given annotations.
     *
     * @param annotations annotations to analyse.
     * @return discovered links.
     */
    @JvmStatic
    fun getLinks(annotations: Collection<Annotation>): Set<Link> =
        extractMetaAnnotations(LinkAnnotation::class.java, ::extractLinks, annotations).toSet()

    /**
     * Returns labels created from Allure meta annotations specified on annotated element.
     * Shortcut for [getLinks]
     *
     * @param annotatedElement the element to search annotations on.
     * @return discovered labels.
     */
    @JvmStatic
    fun getLabels(annotatedElement: AnnotatedElement): Set<Label> {
        return getLabels(*annotatedElement.declaredAnnotations)
    }

    /**
     * Shortcut for [getLabels].
     *
     * @param annotations annotations to analyse.
     * @return discovered labels.
     */
    @JvmStatic
    fun getLabels(vararg annotations: Annotation): Set<Label> {
        return getLabels(annotations.toList())
    }

    /**
     * Returns labels from given annotations.
     *
     * @param annotations annotations to analyse.
     * @return discovered labels.
     */
    @JvmStatic
    fun getLabels(annotations: Collection<Annotation>): Set<Label> =
        extractMetaAnnotations(LabelAnnotation::class.java, ::extractLabels, annotations).toSet()

    private fun <Result, AnnotationClazz : Annotation> extractMetaAnnotations(
        annotationType: Class<AnnotationClazz>,
        mapper: (AnnotationClazz, Annotation) -> List<Result>,
        candidates: Collection<Annotation>
    ): List<Result> {
        val visited: MutableSet<Annotation> = HashSet()
        return candidates
            .flatMap(this::extractRepeatable)
            .flatMap { candidate ->
                extractMetaAnnotations(annotationType, mapper, candidate, visited)
            }
    }

    private fun <Result, AnnotationClazz : Annotation> extractMetaAnnotations(
        annotationType: Class<AnnotationClazz>,
        mapper: (AnnotationClazz, Annotation) -> List<Result>,
        candidate: Annotation,
        visited: MutableSet<Annotation>
    ): List<Result> {
        if (!isOperationalAnnotation(candidate.annotationType()) && visited.add(candidate)) {
            val children = candidate.annotationType().annotations
                .flatMap { extractRepeatable(it) }
                .flatMap { extractMetaAnnotations(annotationType, mapper, it, visited) }

            val current = listOfNotNull(candidate.annotationType().getAnnotation(annotationType))
                .flatMap { marker -> mapper(marker, candidate) }
            return current + children
        }
        return emptyList()
    }

    private fun extractLabels(m: LabelAnnotation, annotation: Annotation): List<Label> =
        when (m.value) {
            LabelAnnotation.DEFAULT_VALUE ->
                callValueMethod(annotation).map { createLabel(m.name, it) }
            else -> listOf(createLabel(m.name, m.value))
        }

    private fun extractLinks(
        m: LinkAnnotation,
        annotation: Annotation
    ): List<Link> {
        // this is required as Link annotation uses name attribute as value alias.
        if (annotation is io.qameta.allure.android.annotations.Link) {
            return listOf(createLink(annotation))
        }
        return when (m.value) {
            LinkAnnotation.DEFAULT_VALUE ->
                callValueMethod(annotation).map { createLink(it, null, m.url, m.type) }
            else -> listOf(createLink(m.value, null, m.url, m.type))
        }
    }

    private fun callValueMethod(annotation: Annotation): List<String> {
        return try {
            val method: Method = annotation.annotationType().getMethod(VALUE_METHOD_NAME)
            val `object` = method.invoke(annotation)
            objectToStringStream(`object`)
        } catch (e: NoSuchMethodException) {
            emptyList()
        } catch (e: IllegalAccessException) {
            emptyList()
        } catch (e: InvocationTargetException) {
            emptyList()
        }
    }

    private fun objectToStringStream(data: Any?): List<String> {
        if (data != null && data.javaClass.isArray) {
            when (data) {
                is LongArray -> return data.map(Long::toString)
                is ShortArray -> return data.map(Short::toString)
                is IntArray -> return data.map(Int::toString)
                is CharArray -> return data.map(Char::toString)
                is DoubleArray -> return data.map(Double::toString)
                is FloatArray -> return data.map(Float::toString)
                is BooleanArray -> return data.map(Boolean::toString)
                is ByteArray -> return data.map(Byte::toString)
                is Array<*> -> return data.map(Any?::toString)
            }
        }
        return listOf(Objects.toString(data))
    }


    private fun extractRepeatable(annotation: Annotation): List<Annotation> {
        return if (isRepeatableWrapper(annotation)) {
            try {
                val method: Method = annotation.annotationType().getMethod(VALUE_METHOD_NAME)
                (method.invoke(annotation) as Array<Annotation>).toList()
            } catch (e: NoSuchMethodException) {
                emptyList<Annotation>()
            } catch (e: IllegalAccessException) {
                emptyList<Annotation>()
            } catch (e: InvocationTargetException) {
                emptyList<Annotation>()
            }
        } else listOf(annotation)
    }

    private fun isRepeatableWrapper(annotation: Annotation): Boolean {
        return annotation.annotationType().declaredMethods.asSequence()
            .filter { method: Method ->
                VALUE_METHOD_NAME.equals(method.name, ignoreCase = true)
            }
            .filter { method: Method -> method.returnType.isArray }
            .any { method: Method -> isRepeatable(method.returnType.componentType) }
    }

    private fun isRepeatable(annotationType: Class<*>): Boolean {
        return annotationType.isAnnotationPresent(Repeatable::class.java)
    }

    private fun isOperationalAnnotation(annotationType: Class<out Annotation>?): Boolean {
        return isInJavaLangAnnotationPackage(annotationType) || isKotlinMetadata(annotationType)
    }

    private fun isInJavaLangAnnotationPackage(annotationType: Class<out Annotation>?): Boolean {
        return annotationType != null && annotationType.name.startsWith("java.lang.annotation")
    }

    private fun isKotlinMetadata(annotationType: Class<out Annotation>?): Boolean {
        return annotationType != null && annotationType.name.startsWith("kotlin.Metadata")
    }
}

private fun <T : Annotation> T.annotationType(): Class<out T> =
    (this as java.lang.annotation.Annotation).annotationType() as Class<out T>