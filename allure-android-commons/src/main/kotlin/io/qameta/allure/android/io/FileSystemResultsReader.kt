package io.qameta.allure.android.io

import io.qameta.allure.android.model.TestResult
import io.qameta.allure.android.model.TestResultContainer
import io.qameta.allure.android.serialization.SerializationProcessor
import io.qameta.allure.android.serialization.gson.GsonSerializationProcessor
import java.io.File

/**
 * @author Badya on 18.04.2017.
 */
open class FileSystemResultsReader(val resultsDir: File = getDefaultResultsDir(),
                                   val serializationProcessor: SerializationProcessor = GsonSerializationProcessor) : AllureResultsReader {

    companion object {
        @JvmStatic
        fun getDefaultResultsDir() = File(System.getProperty("allure.results.directory", "build/allure-results"))
    }

    override fun getAttachmentFile(src: String): File = File(resultsDir, src)

    override fun <T> read(uuid: String, type: Class<T>): T {
        val file = when (type) {
            TestResult::javaClass -> File(generateTestResultName(uuid))
            TestResultContainer::javaClass -> File(generateTestResultContainerName(uuid))
            else -> throw AllureResultsReadException("unknown type $type")
        }

        return serializationProcessor.deserialize(file, type)
    }

    fun listResults(): Array<out File>? = resultsDir.listFiles()
}

class AllureResultsReadException : RuntimeException {
    constructor(message: String?, cause: Throwable) : super(message, cause)
    constructor(message: String?) : super(message)
}