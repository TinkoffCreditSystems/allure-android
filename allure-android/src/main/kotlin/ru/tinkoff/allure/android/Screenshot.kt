package ru.tinkoff.allure.android

import android.os.Environment
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import java.util.concurrent.TimeUnit

/**
 * @author Badya on 31.05.2017.
 */
fun deviceScreenshot(tag: String) {
    val file = createTempFile("attachment", null, Environment.getExternalStorageDirectory())
    with(UiDevice.getInstance(getInstrumentation())) {
        waitForIdle(TimeUnit.SECONDS.toMillis(5))
        takeScreenshot(file)
    }
    AllureAndroidLifecycle.addAttachment(name = tag, type = "image/png", fileExtension = ".png", file = file)
}
