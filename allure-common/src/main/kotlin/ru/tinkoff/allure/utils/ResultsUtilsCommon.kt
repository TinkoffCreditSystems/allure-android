package ru.tinkoff.allure.utils

import org.junit.runner.Description
import ru.tinkoff.allure.SeverityLevel
import ru.tinkoff.allure.annotations.DisplayName
import ru.tinkoff.allure.annotations.Issue
import ru.tinkoff.allure.annotations.Owner
import ru.tinkoff.allure.annotations.Severity
import ru.tinkoff.allure.model.Label
import ru.tinkoff.allure.model.Link
import java.math.BigInteger
import java.security.MessageDigest

/**
 * @author Badya on 01.06.2017.
 */

const val ISSUE_LINK_TYPE = "issue"
const val SEVERITY_LABEL_NAME = "severity"
const val TAG_LABEL_NAME = "tag"
const val OWNER_LABEL_NAME = "owner"

fun getMethodDisplayName(description: Description): String {
    return description.getAnnotation(DisplayName::class.java)?.value ?: description.methodName
}

fun getClassDisplayName(description: Description): String? {
    return description.testClass.getAnnotation(DisplayName::class.java)?.value
            ?: description.className
}

fun createTagLabel(tag: String): Label {
    return createLabel(TAG_LABEL_NAME, tag)
}

fun createOwnerLabel(owner: String): Label {
    return createLabel(OWNER_LABEL_NAME, owner)
}

fun createLabel(owner: Owner): Label {
    return createOwnerLabel(owner.value)
}

fun createSeverityLabel(severity: SeverityLevel): Label {
    return createSeverityLabel(severity.value())
}

fun createSeverityLabel(severity: String): Label {
    return createLabel(SEVERITY_LABEL_NAME, severity)
}

fun createLabel(severity: Severity): Label {
    return createSeverityLabel(severity.value)
}

fun createLabel(name: String, value: String): Label {
    return Label(name, value)
}

fun createLink(link: ru.tinkoff.allure.annotations.Link): Link {
    return createLink(link.value, link.name, link.url, link.type)
}

fun createLink(link: Issue): Link {
    return createIssueLink(link.value)
}

fun createIssueLink(value: String): Link {
    return createLink(value, "", null, ISSUE_LINK_TYPE)
}

fun createLink(value: String?, name: String?, url: String?, type: String): Link {
    return Link(name = value ?: name, url = url ?: getLinkUrl(name, type), type = type)
}

fun getLinkUrl(name: String?, type: String): String? {
    val pattern = System.getProperty("allure.link.$type.pattern") ?: return null
    return pattern.replace("\\{}", name ?: "")
}

fun getLinks(description: Description): List<Link> {
    return getAnnotationsOnClass(description, ru.tinkoff.allure.annotations.Link::class.java).map { createLink(it) } +
            getAnnotationsOnMethod(description, ru.tinkoff.allure.annotations.Link::class.java).map { createLink(it) } +
            getAnnotationsOnClass(description, Issue::class.java).map { createLink(it) } +
            getAnnotationsOnMethod(description, Issue::class.java).map { createLink(it) }
}

fun getLabels(description: Description): List<Label> {
    return getAnnotationsOnClass(description, Owner::class.java).map { createLabel(it) } +
            getAnnotationsOnMethod(description, Owner::class.java).map { createLabel(it) } +
            getAnnotationsOnClass(description, Severity::class.java).map { createLabel(it) } +
            getAnnotationsOnMethod(description, Severity::class.java).map { createLabel(it) }
}

fun <T : Annotation> getAnnotationsOnMethod(description: org.junit.runner.Description, clazz: Class<T>): List<T> {
    return listOfNotNull(description.getAnnotation(clazz)) + extractRepeatable(clazz)
}

fun <T : Annotation> getAnnotationsOnClass(description: org.junit.runner.Description, clazz: Class<T>): List<T> {
    return listOfNotNull(description.testClass.getAnnotation(clazz))
}

@Suppress("UNUSED_PARAMETER")
fun <T : Annotation> extractRepeatable(clazz: Class<T>): List<T> {
    // TODO: support Repeatable
    return emptyList()
}

fun getPackage(testClass: Class<*>): String {
    return testClass.`package`.name
}

fun getHistoryId(description: Description): String {
    return BigInteger(1, md5(description.className + description.methodName)).toString(16)
}

fun md5(source: String): ByteArray {
    return MessageDigest.getInstance("md5").digest(source.toByteArray())
}