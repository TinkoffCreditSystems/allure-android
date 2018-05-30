package ru.tinkoff.allure.android

import android.os.Environment
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.uiautomator.UiDevice
import java.util.concurrent.TimeUnit

/**
 * @author Badya on 31.05.2017.
 */
fun deviceScreenshot(tag: String) {
    try {
        val file = createTempFile("attachment", null, Environment.getExternalStorageDirectory())
        with(UiDevice.getInstance(getInstrumentation())) {
            waitForIdle(TimeUnit.SECONDS.toMillis(5))
            takeScreenshot(file)
        }
        AllureAndroidLifecycle.addAttachment(name = tag, type = "image/png", fileExtension = ".png", file = file)
    }catch (e:Exception){
        e.printStackTrace()
    }
}
