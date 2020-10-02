package io.qameta.allure.android


import androidx.test.runner.AndroidJUnit4
import io.qameta.allure.android.annotations.DisplayName
import io.qameta.allure.android.annotations.Epic
import io.qameta.allure.android.annotations.Features
import io.qameta.allure.android.annotations.Feature
import io.qameta.allure.android.annotations.Issue
import io.qameta.allure.android.annotations.Stories
import io.qameta.allure.android.annotations.Story
import io.qameta.allure.android.annotations.TmsLink
import io.qameta.allure.android.annotations.Severity
import io.qameta.allure.android.annotations.Tags
import io.qameta.allure.android.annotations.Tag
import io.qameta.allure.android.annotations.Owner
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import io.qameta.allure.espresso.FailshotRule
import io.qameta.allure.espresso.LogcatClearRule
import io.qameta.allure.espresso.LogcatDumpRule
import io.qameta.allure.espresso.WindowHierarchyRule
import io.qameta.allure.espresso.deviceScreenshot
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
@Epic("Epic-1")
@Features(
        Feature("Feature-1"),
        Feature("Feature-2")
)
@Stories(
        Story("Story-1"),
        Story("Story-2")
)
@Tags(
        Tag("Tag-1"),
        Tag("Tag-1")
)
class AllureTest {

    @get:Rule
    val ruleChain = RuleChain.outerRule(LogcatClearRule())
            .around(FailshotRule())
            .around(WindowHierarchyRule())
            .around(LogcatDumpRule())

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

    @Test
    fun shouldAddScreenshot() {
        deviceScreenshot("my screenshot")
    }
}