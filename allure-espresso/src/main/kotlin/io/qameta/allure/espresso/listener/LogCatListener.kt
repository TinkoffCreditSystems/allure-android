package io.qameta.allure.espresso.listener

import android.util.Log
import io.qameta.allure.android.listener.ContainerLifecycleListener
import io.qameta.allure.android.listener.StepLifecycleListener
import io.qameta.allure.android.listener.TestLifecycleListener
import io.qameta.allure.android.model.StepResult
import io.qameta.allure.android.model.TestResult
import io.qameta.allure.android.model.TestResultContainer

/**
 * @author Badya on 22.05.2017.
 */
class LogCatListener : ContainerLifecycleListener, TestLifecycleListener, StepLifecycleListener {
    companion object {
        const val TAG = "AllureAndroidLifecycle"
    }

    override fun beforeContainerStart(container: TestResultContainer) {
        debug("START", container)
    }

    override fun beforeContainerUpdate(container: TestResultContainer) {
        debug("UPDATE", container)
    }

    override fun beforeContainerStop(container: TestResultContainer) {
        debug("STOP", container)
    }

    override fun beforeContainerWrite(container: TestResultContainer) {
        debug("WRITE", container)
    }

    override fun beforeTestSchedule(result: TestResult) {
        debug("SCHEDULE", result)
    }

    override fun beforeTestStart(result: TestResult) {
        debug("START", result)
    }

    override fun beforeTestUpdate(result: TestResult) {
        debug("UPDATE", result)
    }

    override fun beforeTestStop(result: TestResult) {
        debug("STOP", result)
    }

    override fun beforeTestWrite(result: TestResult) {
        debug("WRITE", result)
    }

    override fun beforeStepStart(result: StepResult) {
        debug("START", result)
    }

    override fun beforeStepUpdate(result: StepResult) {
        debug("UPDATE", result)
    }

    override fun beforeStepStop(result: StepResult) {
        debug("STOP", result)
    }

    private fun debug(action: String, entity: Any) {
        Log.d(TAG, when (entity) {
            is TestResult -> "$action test: ${entity.uuid} (${entity.fullName})"
            is TestResultContainer -> "$action container: ${entity.uuid}"
            is StepResult -> "$action step: ${entity.uuid}"
            else -> ""
        })
    }
}