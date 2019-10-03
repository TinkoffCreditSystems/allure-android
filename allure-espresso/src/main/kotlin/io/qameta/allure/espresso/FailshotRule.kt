package io.qameta.allure.espresso

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * @author Badya on 25.05.2017.
 */
@Suppress("unused")
class FailshotRule : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                try {
                    base.evaluate()
                } catch (t: Throwable) {
                    failshot()
                    throw t
                }
            }
        }
    }

    private fun failshot() {
        deviceScreenshot("failshot")
    }
}