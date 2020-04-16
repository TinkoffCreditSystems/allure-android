package io.qameta.allure.android.utils

import io.qameta.allure.android.SeverityLevel
import io.qameta.allure.android.annotations.*
import io.qameta.allure.android.model.Label
import io.qameta.allure.android.model.Link
import org.junit.runner.Description
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
const val LAYER_LABEL_NAME = "layer"
const val ISSUE_LABEL_TYPE = "issue"
const val ALLURE_ID_LABEL_NAME = "AS_ID"


fun getMethodDisplayName(description: Description): String {
    return description.getAnnotation(DisplayName::class.java)?.value ?: description.methodName
}

fun getClassDisplayName(description: Description): String? {
    return description.testClass.getAnnotation(DisplayName::class.java)?.value
            ?: description.className
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

fun createLink(issue: Issue): Link {
    return createIssueLink(issue.value)
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

fun createLabels(stories: Stories): List<Label> {
    return stories.value.map { createLabel(it) }
}

fun createLabel(story: Story): Label {
    return createStoryLabel(story.value)
}

fun createLabel(layer: Layer): Label {
    return createLabel(name = LAYER_LABEL_NAME, value = layer.value)
}

fun createLabels(tags: Tags): List<Label> {
    return tags.value.map { createLabel(it) }
}

fun createLabel(tag: Tag): Label {
    return createLabel(name = TAG_LABEL_NAME, value = tag.value)
}

fun createLabels(tags: Issues): List<Label> {
    return tags.value.map { createLabel(it) }
}

fun createLabel(issue: Issue): Label {
    return createLabel(name = ISSUE_LABEL_TYPE, value = issue.value)
}

fun createLabel(allureId: AllureId): Label {
    return createAllureIdLabel(allureId.value)
}

fun createAllureIdLabel(value: String): Label {
    return createLabel(name = ALLURE_ID_LABEL_NAME, value = value)
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

            getAnnotationsOnClass(description, Layer::class.java).map { createLabel(it) } +
            getAnnotationsOnMethod(description, Layer::class.java).map { createLabel(it) } +

            getAnnotationsOnClass(description, Issues::class.java).flatMap { createLabels(it) } +
            getAnnotationsOnMethod(description, Issues::class.java).flatMap { createLabels(it) } +

            getAnnotationsOnClass(description, Issue::class.java).map { createLabel(it) } +
            getAnnotationsOnMethod(description, Issue::class.java).map { createLabel(it) } +

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
            getAnnotationsOnMethod(description, Story::class.java).map { createLabel(it) } +

            getAnnotationsOnClass(description, Tags::class.java).flatMap { createLabels(it) } +
            getAnnotationsOnMethod(description, Tags::class.java).flatMap { createLabels(it) } +

            getAnnotationsOnClass(description, Tag::class.java).map { createLabel(it) } +
            getAnnotationsOnMethod(description, Tag::class.java).map { createLabel(it) } +

            getAnnotationsOnMethod(description, AllureId::class.java).map { createLabel(it) }
}

fun <T : Annotation> getAnnotationsOnMethod(description: Description, clazz: Class<T>): List<T> {
    return listOfNotNull(description.getAnnotation(clazz)) + extractRepeatable(clazz)
}

fun <T : Annotation> getAnnotationsOnClass(description: Description, clazz: Class<T>): List<T> {
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
