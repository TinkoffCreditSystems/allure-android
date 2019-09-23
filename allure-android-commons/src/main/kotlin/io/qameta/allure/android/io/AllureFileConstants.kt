package io.qameta.allure.android.io

import java.util.*

/**
 * @author Badya on 18.04.2017.
 */
const val TEST_RESULT_FILE_SUFFIX = "-result.json"

val TEST_RESULT_FILE_PATTERN = ".+-result.json".toRegex()

const val TEST_RESULT_FILE_GLOB = "*-result.json"

const val TEST_RESULT_CONTAINER_FILE_SUFFIX = "-container.json"

val TEST_RESULT_CONTAINER_FILE_PATTERN = ".+-container.json".toRegex()

const val TEST_RESULT_CONTAINER_FILE_GLOB = "*-container.json"

const val TEST_RUN_FILE_SUFFIX = "-testrun.json"

const val TEST_RUN_FILE_GLOB = "*-testrun.json"

const val ATTACHMENT_FILE_SUFFIX = "-attachment"

val ATTACHMENT_FILE_PATTERN = ".+-attachment.*".toRegex()

const val TXT_EXTENSION = ".txt"

const val TEXT_PLAIN = "text/plain"

const val PNG_EXTENSION = ".png"

const val IMAGE_PNG = "image/png"

const val XML_EXTENSION = ".xml"

const val TEXT_XML = "text/xml"

fun generateTestResultName(uuid: String) = uuid + TEST_RESULT_FILE_SUFFIX

fun generateTestResultName() = generateTestResultName(UUID.randomUUID().toString())

fun generateTestResultContainerName() = generateTestResultContainerName(UUID.randomUUID().toString())

fun generateTestResultContainerName(uuid: String) = uuid + TEST_RESULT_CONTAINER_FILE_SUFFIX

fun generateAttachmentFileName(uuid: String, fileExtension: String?) = uuid + ATTACHMENT_FILE_SUFFIX + getExtension(fileExtension)

fun getExtension(fileExtension: String?): String {
    if (fileExtension.isNullOrBlank()) {
        return ""
    }
    return if (fileExtension?.get(0) == '.') fileExtension else ".$fileExtension"
}