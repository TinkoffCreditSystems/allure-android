package io.qameta.allure.android.annotations

import java.lang.annotation.Inherited

/**
 * Marker annotation. Annotations marked by this annotation will be discovered
 * by Allure and added to test results as a label.
 *
 * @see Epic
 *
 * @see Feature
 *
 * @see Story
 */
@MustBeDocumented
@Inherited
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS)
/**
 * Due do compatibility with Java 1.6, Kotlin doesn't support repeatable annotations.
 * This annotation is still required in here, because of generic parsing of annotation containers
 * @see [AnnotationUtils.extractRepeatable]
 */
@Repeatable
annotation class LabelAnnotation(
    /**
     * The name of label. Some build-in names can be
     * found in [io.qameta.allure.util.ResultsUtils]. You can
     * also use any custom label name and create mapping for it in
     * Allure Enterprise or Allure 3.
     *
     * @return the name of label to add.
     */
    val name: String,
    /**
     * Th value of label. In not specified will take value from `value()`
     * method of target annotation.
     *
     * @return the value of label to add.
     */
    val value: String = DEFAULT_VALUE
) {
    companion object {
        const val DEFAULT_VALUE = "$$$$$$$\$__value__$$$$$$$$"
    }
}
