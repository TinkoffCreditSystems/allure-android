package ru.tinkoff.allure.android

import android.os.Bundle
import android.support.annotation.Keep
import android.support.test.runner.AndroidJUnitRunner
import java.lang.StringBuilder

/**
 * @author Badya on 30.03.2017.
 */

@Suppress("unused")
@Keep
open class AllureAndroidRunner : AndroidJUnitRunner() {
    override fun onCreate(arguments: Bundle) {
        val listenersStringBuilder = StringBuilder(arguments.getString("listener"))
        if (listenersStringBuilder.length > 0) {
            listenersStringBuilder.append(",")
        }
        listenersStringBuilder.append(AllureAndroidListener::class.java.name)
        arguments.putCharSequence("listener", listenersStringBuilder.toString())
        super.onCreate(arguments)
    }
}