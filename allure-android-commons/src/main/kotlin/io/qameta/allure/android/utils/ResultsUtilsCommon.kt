package io.qameta.allure.android.utils

import org.junit.runner.Description
import io.qameta.allure.android.SeverityLevel
import io.qameta.allure.android.annotations.DisplayName
import io.qameta.allure.android.annotations.Epic
import io.qameta.allure.android.annotations.Epics
import io.qameta.allure.android.annotations.Feature
import io.qameta.allure.android.annotations.Features
import io.qameta.allure.android.annotations.Issue
import io.qameta.allure.android.annotations.Owner
import io.qameta.allure.android.annotations.Severity
import io.qameta.allure.android.annotations.Stories
import io.qameta.allure.android.annotations.Story
import io.qameta.allure.android.annotations.TmsLink
import io.qameta.allure.android.model.Label
import io.qameta.allure.android.model.Link
import java.math.BigInteger
import java.security.MessageDigest
import java.util.Collections.emptyList


/**
 * @author Badya on 01.06.2017.
 */

const val ISSUE_LINK_TYPE = "issue"
const val TMS_LINK_TYPE = "tms"
const val SEVERITY_LABEL_NAME = "severity"
const val TAG_LABEL_NAME = "tag"
const val OWNER_LABEL_NAME = "owner"
const val EPIC_LABEL_NAME = "epic"
const val FEATURE_LABEL_NAME = "feature"
const val STORY_LABEL_NAME = "story"


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

fun createLink(link: io.qameta.allure.android.annotations.Link): Link {
    return createLink(link.value, link.name, link.url, link.type)
}

fun createLink(link: Issue): Link {
    return createIssueLink(link.value)
}

fun createIssueLink(value: String): Link {
    return createLink(value, "", null, ISSUE_LINK_TYPE)
}

fun createLink(link: TmsLink): Link {
    return createTmsLink(link.value)
}

fun createTmsLink(value: String): Link {
    return createLink(value, "", null, TMS_LINK_TYPE)
}

fun createLink(value: String?, name: String?, url: String?, type: String): Link {
    val linkName = value ?: name
    val linkUrl = url ?: getLinkUrl(linkName, type)
    return Link(linkName, linkUrl, type)
}

fun getLinkUrl(name: String?, type: String): String? {
    val pattern = System.getProperty("allure.link.$type.pattern") ?: return null
    return pattern.replace("{}", name ?: "")
}

fun createLabels(epics: Epics): List<Label> {
    return epics.value.map { createLabel(it) }
}

fun createLabel(epic: Epic): Label {
    return createEpicLabel(epic.value)
}

fun createEpicLabel(value: String): Label {
    return createLabel(name = EPIC_LABEL_NAME, value = value)
}

fun createLabels(features: Features): List<Label> {
    return features.value.map { createLabel(it) }
}

fun createLabel(feature: Feature): Label {
    return createFeatureLabel(feature.value)
}

fun createFeatureLabel(value: String): Label {
    return createLabel(name = FEATURE_LABEL_NAME, value = value)
}

fun createLabels(features: Stories): List<Label> {
    return features.value.map { createLabel(it) }
}

fun createLabel(feature: Story): Label {
    return createStoryLabel(feature.value)
}

fun createStoryLabel(value: String): Label {
    return createLabel(name = STORY_LABEL_NAME, value = value)
}

fun getLinks(description: Description): List<Link> {
    return getAnnotationsOnClass(description, io.qameta.allure.android.annotations.Link::class.java).map { createLink(it) } +
            getAnnotationsOnMethod(description, io.qameta.allure.android.annotations.Link::class.java).map { createLink(it) } +
            getAnnotationsOnClass(description, Issue::class.java).map { createLink(it) } +
            getAnnotationsOnMethod(description, Issue::class.java).map { createLink(it) } +
            getAnnotationsOnMethod(description, TmsLink::class.java).map { createLink(it) }
}

fun getLabels(description: Description): List<Label> {
    return getAnnotationsOnClass(description, Owner::class.java).map { createLabel(it) } +
            getAnnotationsOnMethod(description, Owner::class.java).map { createLabel(it) } +

            getAnnotationsOnClass(description, Severity::class.java).map { createLabel(it) } +
            getAnnotationsOnMethod(description, Severity::class.java).map { createLabel(it) } +

            getAnnotationsOnClass(description, Epics::class.java).flatMap { createLabels(it) } +
            getAnnotationsOnMethod(description, Epics::class.java).flatMap { createLabels(it) } +

            getAnnotationsOnClass(description, Epic::class.java).map { createLabel(it) } +
            getAnnotationsOnMethod(description, Epic::class.java).map { createLabel(it) } +

            getAnnotationsOnClass(description, Features::class.java).flatMap { createLabels(it) } +
            getAnnotationsOnMethod(description, Features::class.java).flatMap { createLabels(it) } +

            getAnnotationsOnClass(description, Feature::class.java).map { createLabel(it) } +
            getAnnotationsOnMethod(description, Feature::class.java).map { createLabel(it) } +

            getAnnotationsOnClass(description, Stories::class.java).flatMap { createLabels(it) } +
            getAnnotationsOnMethod(description, Stories::class.java).flatMap { createLabels(it) } +

            getAnnotationsOnClass(description, Story::class.java).map { createLabel(it) } +
            getAnnotationsOnMethod(description, Story::class.java).map { createLabel(it) }
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