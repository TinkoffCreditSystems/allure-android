package io.qameta.allure.android

import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import io.qameta.allure.android.annotations.DisplayName
import io.qameta.allure.android.annotations.Issue
import io.qameta.allure.android.annotations.Owner
import io.qameta.allure.android.annotations.Severity
import io.qameta.allure.android.annotations.TmsLink
import io.qameta.allure.espresso.FailshotRule
import io.qameta.allure.espresso.LogcatClearRule
import io.qameta.allure.espresso.LogcatDumpRule
import io.qameta.allure.espresso.WindowHierarchyRule
import org.junit.Assume
import org.junit.Ignore
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * @author Badya on 06.06.2017.
 */
@RunWith(AndroidJUnit4::class)
@Issue("ISSUE-123")
@DisplayName("allure-android")
class AllureTest {

    @get:Rule
    val ruleChain = RuleChain.outerRule(LogcatClearRule()).around(FailshotRule())
            .around(WindowHierarchyRule()).around(LogcatDumpRule())

    @Test
    fun shouldAddGreenStep() = step("Step1") {
        assertTrue(true)
    }

    @Test(expected = AssertionError::class)
    fun shouldAddRedStep() {
        step("Step1") {
            fail()
        }
    }

    @Test
    @Owner("owner2")
    @DisplayName("annotations")
    @TmsLink("tms-1")
    @Severity(SeverityLevel.BLOCKER)
    fun shouldAddAnnotations() {
        assertTrue(true)
    }

    @Ignore("Some reason")
    @Test
    fun shouldIgnoredWithSomeReason() {
        assertTrue(true)
    }

    @Test
    @Ignore
    fun shouldIgnored() {
        assertTrue(true)
    }

    @Test
    fun shouldAssumed() {
        Assume.assumeTrue(false)
    }
}