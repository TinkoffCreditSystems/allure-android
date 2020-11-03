package io.qameta.allure.android.gson

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import io.qameta.allure.android.model.Label
import io.qameta.allure.android.model.Link
import io.qameta.allure.android.model.TestResult
import io.qameta.allure.android.serialization.gson.GsonSerializationProcessor
import kotlin.test.assertEquals

/**
 * @author Badya on 01.06.2017.
 */
class GsonSerializationProcessorTest {
    private val testResult =
            TestResult(
                    uuid = "0123456789ABCDEF",
                    name = "Test",
                    historyId = "testHistoryId",
                    fullName = "${this.javaClass.name}.serializeTestResult",
                    links = setOf(Link(name = "myLink")),
                    labels = setOf(Label(name = "label", value = "value"))
            )

    @get:Rule
    val tempFolder = TemporaryFolder()

    @Test
    fun `serialize TestResult`() =
            assertEquals(
                    """{
                      |  "uuid": "${testResult.uuid}",
                      |  "historyId": "${testResult.historyId}",
                      |  "fullName": "${GsonSerializationProcessorTest::class.java.canonicalName}.serializeTestResult",
                      |  "labels": [
                      |    {
                      |      "name": "label",
                      |      "value": "value"
                      |    }
                      |  ],
                      |  "links": [
                      |    {
                      |      "name": "myLink"
                      |    }
                      |  ],
                      |  "name": "${testResult.name}",
                      |  "steps": [],
                      |  "attachments": [],
                      |  "parameters": []
                      |}""".trimMargin(),
                    GsonSerializationProcessor.serialize(testResult), "Serialize TestResult failed"
            )


    @Test
    fun `deserialize TestResult`() {
        val deserialized = GsonSerializationProcessor.deserialize(GsonSerializationProcessor.serialize(testResult), TestResult::class.java)

        assertEquals(testResult.uuid, deserialized.uuid)
        assertEquals(testResult.fullName, deserialized.fullName)
    }
}