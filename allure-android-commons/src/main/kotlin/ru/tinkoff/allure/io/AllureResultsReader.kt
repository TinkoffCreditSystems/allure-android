package ru.tinkoff.allure.io

import java.io.File

/**
 * @author Badya on 18.04.2017.
 */
interface AllureResultsReader {
    fun <T> read(uuid: String, type: Class<T>): T
    fun getAttachmentFile(src: String): File
}