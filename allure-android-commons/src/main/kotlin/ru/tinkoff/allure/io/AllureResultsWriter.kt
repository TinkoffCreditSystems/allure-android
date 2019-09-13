package ru.tinkoff.allure.io

import ru.tinkoff.allure.model.TestResult
import ru.tinkoff.allure.model.TestResultContainer
import java.io.File
import java.io.InputStream

/**
 * @author Badya on 18.04.2017.
 */
interface AllureResultsWriter {
    fun write(testResult: TestResult)
    fun write(testResultContainer: TestResultContainer)
    fun write(dest: String, attachment: InputStream)
    fun copy(src: File, dest: File)
    fun move(src: File, dest: File)
}