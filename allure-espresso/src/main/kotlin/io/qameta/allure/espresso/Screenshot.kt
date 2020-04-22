package io.qameta.allure.espresso

import android.graphics.Bitmap
import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.Tracer
import androidx.test.uiautomator.UiDevice
import io.qameta.allure.android.io.IMAGE_PNG
import io.qameta.allure.android.io.PNG_EXTENSION
import io.qameta.allure.espresso.utils.createAttachmentFile
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Take a screenshot of current window and add it to current test result as attachment
 *
 * @param maxWidth max screenshot width in pixels (ignored if less than actual width)
 * @param scale scale the screenshot down if needed; 1.0f for original size (ignored if maxWidth defined)
 * @param quality quality of screenshot image, 1-100, by default set to 90
 */
fun deviceScreenshot(tag: String,
                     scale: Float? = null,
                     maxWidth: Int? = null,
                     quality: Int? = null) {

    var screenshotTaken: Boolean
    val file = createAttachmentFile()
    with(UiDevice.getInstance(getInstrumentation())) {
        waitForIdle(TimeUnit.SECONDS.toMillis(5))
        screenshotTaken = takeScreenshot(file, scale, maxWidth, quality)
    }

    if (screenshotTaken) {
        AllureAndroidLifecycle.addAttachment(name = tag, type = IMAGE_PNG, fileExtension = PNG_EXTENSION, file = file)
    }
}

/**
 * Take a screenshot of current window and store it as PNG
 * (Fixed version of android.support.test.uiautomator.UiDevice#takeScreenshot)
 *  Screenshot scaling does not work in UI Automator, scale param is not handled properly, had to make local fix.
 *
 * The screenshot is adjusted per screen rotation
 *
 * @param storePath where the PNG should be written to
 * @param maxWidth max screenshot width in pixels (ignored if less than actual width)
 * @param scale scale the screenshot down if needed; 1.0f for original size (ignored if maxWidth defined)
 * @param quality quality of the PNG compression; range: 0-100 (90 by default)
 * @return true if screenshot is created successfully, false otherwise
 * @since API Level 17
 */
private fun takeScreenshot(storePath: File,
                           scale: Float? = null,
                           maxWidth: Int? = null,
                           quality: Int? = null): Boolean {

    Tracer.trace(storePath, scale, quality)
    var screenshot: Bitmap = getInstrumentation().uiAutomation.takeScreenshot()  // requires API level 18
            ?: return false
    var bos: BufferedOutputStream? = null
    try {
        maxWidth?.let {
            screenshot = scaleByMaxWidth(screenshot, maxWidth)
        } ?: scale?.let {
            // only scale by scaling factor if max width is not defined
            screenshot = scaleByScalingFactor(screenshot, scale)
        }

        bos = BufferedOutputStream(FileOutputStream(storePath))
        screenshot.compress(Bitmap.CompressFormat.PNG, quality ?: 90, bos)

        bos.flush()
    } catch (ioe: IOException) {
        Log.e("AllureUtil", "failed to save screenshot to file", ioe)
        return false
    } finally {
        if (bos != null) {
            try {
                bos.close()
            } catch (ioe: IOException) {
                // Ignore
            }
        }
        screenshot.recycle()
    }
    return true
}

private fun scaleByScalingFactor(screenshot: Bitmap, scale: Float): Bitmap {
    val originalWidth = screenshot.width
    val originalHeight = screenshot.height
    val scaledWidth = (originalWidth * scale).toInt()
    val scaledHeight = (originalHeight * scale).toInt()
    return Bitmap.createScaledBitmap(screenshot, scaledWidth, scaledHeight, true)
}

private fun scaleByMaxWidth(screenshot: Bitmap, maxWidth: Int): Bitmap {
    val originalWidth = screenshot.width
    val originalHeight = screenshot.height
    if (originalWidth < maxWidth) {
        return screenshot
    }
    val scalingFactor = (maxWidth.toFloat() / screenshot.width)
    val scaledHeight = (originalHeight * scalingFactor).toInt()
    return Bitmap.createScaledBitmap(screenshot, maxWidth, scaledHeight, true)
}
