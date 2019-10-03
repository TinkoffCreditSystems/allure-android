package io.qameta.allure.espresso

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import io.qameta.allure.android.io.TEXT_PLAIN
import io.qameta.allure.android.io.TXT_EXTENSION
import io.qameta.allure.espresso.utils.createAttachmentFile

class LogcatDumpRule : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                try {
                    base.evaluate()
                } catch (t: Throwable) {
                    dumpLogcat()
                    throw t
                }
            }
        }
    }

    private fun dumpLogcat() {
        val file = createAttachmentFile()
        with(UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())) {
            val logcatResult = executeShellCommand("logcat -d")
            file.writeText(logcatResult)
        }
        AllureAndroidLifecycle.addAttachment(name = "logcat", type = TEXT_PLAIN,
                fileExtension = TXT_EXTENSION, file = file)
    }
}