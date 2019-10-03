package io.qameta.allure.espresso

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import io.qameta.allure.android.io.IMAGE_PNG
import io.qameta.allure.android.io.PNG_EXTENSION
import io.qameta.allure.espresso.utils.createAttachmentFile
import java.util.concurrent.TimeUnit

/**
 * @author Badya on 31.05.2017.
 */
fun deviceScreenshot(tag: String) {
    val file = createAttachmentFile()
    with(UiDevice.getInstance(getInstrumentation())) {
        waitForIdle(TimeUnit.SECONDS.toMillis(5))
        takeScreenshot(file)
    }
    AllureAndroidLifecycle.addAttachment(name = tag, type = IMAGE_PNG, fileExtension = PNG_EXTENSION, file = file)
}
