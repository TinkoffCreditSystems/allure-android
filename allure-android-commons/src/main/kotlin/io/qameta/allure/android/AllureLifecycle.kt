package io.qameta.allure.android

import io.qameta.allure.android.io.AllureResultsReader
import io.qameta.allure.android.io.AllureResultsWriter
import io.qameta.allure.android.io.FileSystemResultsReader
import io.qameta.allure.android.io.FileSystemResultsWriter
import io.qameta.allure.android.io.generateAttachmentFileName
import io.qameta.allure.android.listener.ContainerLifecycleListener
import io.qameta.allure.android.listener.ContainerLifecycleNotifier
import io.qameta.allure.android.listener.StepLifecycleListener
import io.qameta.allure.android.listener.StepLifecycleNotifier
import io.qameta.allure.android.listener.TestLifecycleListener
import io.qameta.allure.android.listener.TestLifecycleNotifier
import io.qameta.allure.android.model.*
import java.io.File
import java.util.*

/**
 * @author Badya on 18.04.2017.
 */
object AllureCommonLifecycle : AllureLifecycle(
        reader = FileSystemResultsReader(),
        writer = FileSystemResultsWriter())

abstract class AllureLifecycle(private val reader: AllureResultsReader,
                               private val writer: AllureResultsWriter) :
        StepLifecycleListener by StepLifecycleNotifier,
        TestLifecycleListener by TestLifecycleNotifier,
        ContainerLifecycleListener by ContainerLifecycleNotifier {

    open fun startTestContainer(parentUuid: String?, container: TestResultContainer) {
        updateTestContainer(parentUuid, { children.add(container.uuid) })
        startTestContainer(container)
    }

    open fun startTestContainer(container: TestResultContainer) {
        AllureStorage.addContainer(container.apply {
            beforeContainerStart(this)
            start = System.currentTimeMillis()
            afterContainerStart(this)
        })
    }

    open fun updateTestContainer(uuid: String?, update: TestResultContainer.() -> Unit) {
        AllureStorage.getContainer(uuid)?.apply {
            beforeContainerUpdate(this)
            update()
            afterContainerUpdate(this)
        }
    }

    open fun stopTestContainer(uuid: String?) {
        AllureStorage.getContainer(uuid)?.apply {
            beforeContainerStop(this)
            stop = System.currentTimeMillis()
            afterContainerStop(this)
        }
    }

    open fun writeTestContainer(uuid: String?) {
        AllureStorage.removeContainer(uuid)?.apply {
            beforeContainerWrite(this)
            writer.write(this)
            afterContainerWrite(this)
        }

    }

    open fun scheduleTestCase(parentUuid: String, result: TestResult) {
        updateTestContainer(parentUuid, { children.add(result.uuid) })
        scheduleTestCase(result)
    }

    open fun scheduleTestCase(result: TestResult) {
        result.apply {
            beforeTestSchedule(this)
            stage = Stage.SCHEDULED
            AllureStorage.addTestResult(this)
            afterTestSchedule(this)
        }
    }

    open fun startTestCase(uuid: String) {
        AllureStorage.getTestResult(uuid)?.apply {
            beforeTestStart(this)
            stage = Stage.RUNNING
            start = System.currentTimeMillis()
            AllureStorage.startTest(uuid)
            afterTestStart(this)
        }

    }

    open fun updateTestCase(update: TestResult.() -> Unit) {
        updateTestCase(AllureStorage.getTest(), update)
    }

    open fun updateTestCase(uuid: String, update: TestResult.() -> Unit) {
        AllureStorage.getTestResult(uuid)?.apply {
            beforeTestUpdate(this)
            update()
            afterTestUpdate(this)
        }
    }

    open fun stopTestCase() = stopTestCase(AllureStorage.getTest())

    open fun stopTestCase(uuid: String) {
        AllureStorage.getTestResult(uuid)?.apply {
            beforeTestStop(this)
            stage = Stage.FINISHED
            stop = System.currentTimeMillis()
            calcStatus()
            steps.forEach { AllureStorage.remove(it.uuid, StepResult::class.java) }
            afterTestStop(this)
        }

    }

    open fun writeTestCase() = writeTestCase(AllureStorage.getTest())

    open fun writeTestCase(uuid: String) {
        AllureStorage.getTestResult(uuid)?.apply {
            beforeTestWrite(this)
            writer.write(this)
            afterTestWrite(this)
        }
        AllureStorage.clearStepContext()
    }

    open fun startStep(step: StepResult) = startStep(AllureStorage.getCurrentStep(), step)

    open fun startStep(parentUuid: String?, step: StepResult) {
        step.apply {
            beforeStepStart(this)
            stage = Stage.RUNNING
            start = System.currentTimeMillis()
            with(AllureStorage) {
                startStep(this@apply.uuid)
                addStep(parentUuid, this@apply)
            }
            afterStepStart(this)
        }
    }

    open fun updateStep(update: StepResult.() -> Unit) = updateStep(AllureStorage.getCurrentStep(), update)

    open fun updateStep(uuid: String?, update: StepResult.() -> Unit) {
        AllureStorage.getStep(uuid).apply {
            beforeStepUpdate(this)
            update()
            afterStepUpdate(this)
        }
    }

    open fun stopStep() = stopStep(AllureStorage.getCurrentStep())

    open fun stopStep(uuid: String?) {
        AllureStorage.getStep(uuid).apply {
            beforeStepStop(this)
            stage = Stage.FINISHED
            stop = System.currentTimeMillis()
            AllureStorage.stopStep()
            afterStepStop(this)
        }
    }

    open fun addAttachment(name: String?, type: String?, fileExtension: String?, file: File) {
        writer.move(file, prepareAttachment(name, type, fileExtension))
    }

    open fun prepareAttachment(name: String?, type: String?, fileExtension: String?): File {
        val fileName = generateAttachmentFileName(UUID.randomUUID().toString(), fileExtension)
        val uuid = AllureStorage.getCurrentStep()
        val attachment = Attachment(
                name = if (name.isNullOrBlank()) null else name,
                type = if (type.isNullOrBlank()) null else type,
                source = fileName)
        AllureStorage.get(uuid, WithAttachments::class.java).attachments.add(attachment)
        return File(attachment.source)
    }
}