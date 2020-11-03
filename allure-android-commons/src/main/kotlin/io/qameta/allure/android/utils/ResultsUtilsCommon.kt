package io.qameta.allure.android.utils

import io.qameta.allure.android.annotations.*
import io.qameta.allure.android.model.Label
import io.qameta.allure.android.model.Link
import org.junit.runner.Description
import java.math.BigInteger
import java.security.MessageDigest


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

const val CUSTOM_LINK_TYPE = "custom"


fun getMethodDisplayName(description: Description): String {
    return description.getAnnotation(DisplayName::class.java)?.value ?: description.methodName
}

fun getClassDisplayName(description: Description): String? {
    return description.testClass.getAnnotation(DisplayName::class.java)?.value
            ?: description.className
}

fun createLabel(name: String, value: String): Label {
    return Label(name, value)
}

fun createLink(link: io.qameta.allure.android.annotations.Link): Link {
    return createLink(link.value, link.name, link.url, link.type)
}

fun createIssueLink(value: String): Link {
    return createLink(value, "", null, ISSUE_LINK_TYPE)
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

fun getPackage(testClass: Class<*>): String {
    return testClass.`package`.name
}

fun getHistoryId(description: Description): String {
    return BigInteger(1, md5(description.className + description.methodName)).toString(16)
}

fun md5(source: String): ByteArray {
    return MessageDigest.getInstance("md5").digest(source.toByteArray())
}
