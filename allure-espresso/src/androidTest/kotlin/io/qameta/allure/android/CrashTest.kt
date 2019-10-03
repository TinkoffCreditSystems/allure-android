package io.qameta.allure.android

import android.content.Intent
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CrashTest {
    @get:Rule
    val activityRule = ActivityTestRule<CrashTestActivity>(CrashTestActivity::class.java, true, false)

    // adb shell am instrument -w -r   -e debug false -e class io.qameta.allure.android.CrashTest#crash io.qameta.allure.android.test/io.qameta.allure.espresso.AllureAndroidRunner
    @Test
    fun crash() {
        activityRule.launchActivity(Intent(InstrumentationRegistry.getTargetContext().applicationContext, CrashTestActivity::class.java))
        onView(withId(R.id.crash_button)).perform(click())
    }

    // adb shell am instrument -w -r   -e debug false -e class io.qameta.allure.android.CrashTest#rxCrash io.qameta.allure.android.test/io.qameta.allure.espresso.AllureAndroidRunner
    @Test
    fun rxCrash() {
        activityRule.launchActivity(Intent(InstrumentationRegistry.getTargetContext().applicationContext, CrashTestActivity::class.java))
        onView(withId(R.id.rx_crash_button)).perform(click())
    }
}