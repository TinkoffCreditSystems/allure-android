package io.qameta.allure.espresso

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class LogcatClearRule : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                try {
                    clearLogcat()
                    base.evaluate()
                } catch (t: Throwable) {
                    throw t
                }
            }
        }
    }

    private fun clearLogcat() {
        with(UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())) {
            executeShellCommand("logcat -c")
        }
    }
}