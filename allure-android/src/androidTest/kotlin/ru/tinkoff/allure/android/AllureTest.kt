package ru.tinkoff.allure.android

import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import ru.tinkoff.allure.SeverityLevel
import ru.tinkoff.allure.annotations.DisplayName
import ru.tinkoff.allure.annotations.Issue
import ru.tinkoff.allure.annotations.Owner
import ru.tinkoff.allure.annotations.Severity
import ru.tinkoff.allure.step
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
    val ruleChain = RuleChain.outerRule(FailshotRule()).around(WindowHierarchyRule()).around(LogcatRule())

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
    @Severity(SeverityLevel.BLOCKER)
    fun shouldAddAnnotations() {
        assertTrue(true)
    }
}