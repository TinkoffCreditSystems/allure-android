package io.qameta.allure.espresso

import android.os.Build
import android.os.Environment
import io.qameta.allure.android.AllureLifecycle
import io.qameta.allure.android.io.FileSystemResultsReader
import io.qameta.allure.android.io.FileSystemResultsWriter
import java.io.File

/**
 * @author Badya on 06.06.2017.
 */
object AllureAndroidLifecycle : AllureLifecycle(
        FileSystemResultsReader(obtainDirectory("allure-results")),
        FileSystemResultsWriter(obtainDirectory("allure-results")))

fun obtainDirectory(path: String): File {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        File(Environment.getExternalStorageDirectory(), path)
    } else {
        // we can't get context through InstrumentationRegistry
        // because it may not be ready yet when obtainDirectory method calls.
        // So we try to get context through ContextHolder and write
        // test in /data/data/<target_app_bundle_id>/allure-results
        val context = ContextHolder.getTargetAppContext()
        val applicationInfo = context.applicationInfo
        File(applicationInfo.dataDir, path)
    }.apply { if (!exists()) mkdirs() }
}