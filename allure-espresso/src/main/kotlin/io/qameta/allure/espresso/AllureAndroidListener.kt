package io.qameta.allure.espresso

import android.os.Build
import androidx.test.internal.runner.listener.InstrumentationRunListener
import androidx.test.uiautomator.UiDevice
import io.qameta.allure.android.AllureRunListener
import org.junit.runner.Description
import org.junit.runner.Result
import org.junit.runner.notification.Failure

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
        allureListenerDelegate.testFailure(failure)
    }

    override fun testRunStarted(description: Description?) {
        grantPermissions()
        allureListenerDelegate.testRunStarted()
    }

    override fun testRunFinished(result: Result?) {
        allureListenerDelegate.testRunFinished(result)
    }

    override fun testIgnored(description: Description) {
        allureListenerDelegate.testIgnored(description)
    }

    override fun testAssumptionFailure(failure: Failure) {
       allureListenerDelegate.testAssumptionFailure(failure)
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