package io.qameta.allure.espresso.utils

import android.util.Base64
import io.qameta.allure.android.annotations.Description
import io.qameta.allure.android.model.ExecutableItem
import io.qameta.allure.android.utils.md5
import java.lang.reflect.Method

/**
 * @author Badya on 29.05.2017.
 */

const val ALLURE_DESCRIPTIONS_PACKAGE = "allureDescriptions/"

fun processDescription(classLoader: ClassLoader, method: Method, item: ExecutableItem) {
    if (method.isAnnotationPresent(Description::class.java)) {
        if (method.getAnnotation(Description::class.java).useJavaDoc) {
            item.apply { descriptionHtml = classLoader.getResource(ALLURE_DESCRIPTIONS_PACKAGE + generateMethodSignatureHash(method)).readText() }
        } else {
            item.apply { descriptionHtml = method.getAnnotation(Description::class.java).value }
        }
    }
}

fun generateMethodSignatureHash(method: Method): String {
    val signature = method.name + method.parameterTypes.map { it.canonicalName }.joinToString(separator = " ")
    return Base64.encodeToString(md5(signature), Base64.URL_SAFE)
}