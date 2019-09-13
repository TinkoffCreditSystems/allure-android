package ru.tinkoff.allure.serialization.gson

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import ru.tinkoff.allure.model.Label
import ru.tinkoff.allure.model.Link
import ru.tinkoff.allure.model.TestResult
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
                    links = listOf(Link(name = "myLink")),
                    labels = listOf(Label(name = "label", value = "value"))
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