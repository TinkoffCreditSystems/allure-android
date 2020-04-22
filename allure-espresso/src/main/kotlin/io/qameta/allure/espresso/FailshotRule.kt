package io.qameta.allure.espresso

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * JUnit Rule to take current window screenshot and add it to Allure test result as attachment in case of failures.
 *
 * Usage examples:
 * 1. Default - Screenshot will not scale, image quality by default is 90%
 *
 *  @get:Rule
 *  val failshot = FailshotRule()
 *
 * 2. Scale image by defining width in pixels
 *  @get:Rule
 *  val failshot = FailshotRule(maxWidth = 600, quality = 90)
 *
 * 3. Scale image by %. If 0.5f is defined, resulting screenshot size will be half of original
 *  @get:Rule
 *  val failshot = FailshotRule(scale = 0.5f, quality = 90)
 *
 * 4. Only reduce quality (screenshot will be saved with 20% quality)
 *  @get:Rule
 *  val failshot = FailshotRule(quality = 20)
 *
 * @param maxWidth max screenshot width in pixels (ignored if less than actual width)
 * @param scale scale the screenshot down if needed; 1.0f for original size (ignored if maxWidth defined)
 * @param quality quality of screenshot image, 1-100, by default set to 90
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
class FailshotRule(val maxWidth: Int? = null,
                   val scale: Float? = null,
                   val quality: Int? = null) : TestRule {

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
        deviceScreenshot(tag = "failshot", scale = scale, maxWidth = maxWidth, quality = quality)
    }
}