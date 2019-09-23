package io.qameta.allure.android.utils

import android.os.Environment
import java.io.File

fun createAttachmentFile(): File {
    return createTempFile("attachment", null, Environment.getExternalStorageDirectory())
}