package io.qameta.allure.espresso

import android.os.Build
import androidx.test.internal.runner.listener.InstrumentationRunListener
import androidx.test.uiautomator.UiDevice
import io.qameta.allure.android.AllureRunListener
import org.junit.runner.Description
import org.junit.runner.Result
import org.junit.runner.notification.Failure
import io.qameta.allure.android.model.Status
import io.qameta.allure.android.model.StatusDetails

/**
 * @author Badya on 05.06.2017.
 */
class AllureAndroidListener : InstrumentationRunListener() {
    private val lifecycle = AllureAndroidLifecycle
    private val allureListenerDelegate = AllureRunListener(lifecycle)

    override fun testStarted(description: Description) {
        allureListenerDelegate.testStarted(description)
    }

    override fun testFinished(description: Description) {
        allureListenerDelegate.testFinished(description)
    }

    override fun testFailure(failure: Failure) {
        if (failure.description.isTest) {
            val uuid = "${failure.description.className}#${failure.description.methodName}"
            testFailed(uuid, failure)
        } else {
            suiteFailed(failure)
        }
    }

    override fun testRunStarted(description: Description?) {
        grantPermissions()
        allureListenerDelegate.testRunStarted()
    }

    override fun testRunFinished(result: Result?) {
        allureListenerDelegate.testRunFinished()
    }

    private fun testFailed(uuid: String, failure: Failure) {
        with(lifecycle) {
            updateTestCase(uuid) {
                status = Status.fromThrowable(failure.exception)
                statusDetails = StatusDetails.fromThrowable(failure.exception)
            }
            writeTestCase(uuid)
        }
    }

    private fun suiteFailed(failure: Failure) {
        failure.description.children.forEach {
            val uuid = "${it.className}#${it.methodName}"
            testFailed(uuid, failure)
        }
    }

    private fun grantPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            with(UiDevice.getInstance(instrumentation)) {
                executeShellCommand("pm grant " + instrumentation.context.packageName + " android.permission.WRITE_EXTERNAL_STORAGE")
                executeShellCommand("pm grant " + instrumentation.targetContext.packageName + " android.permission.WRITE_EXTERNAL_STORAGE")
                executeShellCommand("pm grant " + instrumentation.context.packageName + " android.permission.READ_EXTERNAL_STORAGE")
                executeShellCommand("pm grant " + instrumentation.targetContext.packageName + " android.permission.READ_EXTERNAL_STORAGE")
            }
        }
    }
}