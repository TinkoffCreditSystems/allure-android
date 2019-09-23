package io.qameta.allure.android

import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.isEmptyOrNullString
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import io.qameta.allure.android.model.Status
import io.qameta.allure.android.model.StepResult
import io.qameta.allure.android.model.TestResult
import kotlin.test.assertTrue
import kotlin.test.fail


/**
 * @author b.mukvich on 01.06.2017.
 */
@RunWith(AllureRunner::class)
class SoftAssertTest {
    @After
    fun after() {
        fun verify(it: TestResult?) {
            fun checkStepResult(stepResult: StepResult, text: String) {
                assertTrue { stepResult.statusDetails?.message!!.contains(text) }
                assertTrue { stepResult.status!! == Status.FAILED }
            }

            if (it?.fullName!!.contains("check_soft_asserted_after_test")) {
                checkStepResult(it.steps[0], "FirstAssert")
            } else if (it.fullName!!.contains("each_step_has_own_softAsserts")) {
                checkStepResult(it.steps[0], "FirstAssert")
                checkStepResult(it.steps[1], "SecondAssert")
            } else if (it.fullName!!.contains("nested_steps_has_own_softAsserts")) {
                checkStepResult(it.steps[0], "FirstAssert")
                checkStepResult(it.steps[0].steps[0], "SecondAssert")
            }
        }

        verify(AllureStorage.getTestResult(AllureStorage.getTest()))
    }

    @Test
    fun `no SoftAssert usage`() {
        step("The Only One") {
            assertTrue { true }
        }
    }

    @Test
    fun `softAssert inTest`() {
        softly { checkThat("FirstAssert", true, equalTo(false)) }
    }

    @Test
    fun `softAsserted after test`() {
        step("The Only One") {
            softly {
                checkThat("FirstAssert", true, equalTo(false))
            }
            val assertedSoftly = true
            assertTrue { assertedSoftly }
        }
    }

    @Test(expected = AssertionError::class)
    fun `softAssert with hardAssert fail test`() {
        step("The Only One") {
            softly { checkThat("FirstAssert", true, equalTo(false)) }
            fail("Fail test")
        }
    }

    @Test
    fun `each step has own softAsserts`() {
        step("First") {
            softly { checkThat("FirstAssert", true, equalTo(false)) }
        }
        step("Second") {
            softly { checkThat("SecondAssert", true, equalTo(false)) }
        }
    }

    @Test
    fun `nested steps has own softAsserts`() {
        step("First") {
            step("Second") {
                softly { checkThat("SecondAssert", true, equalTo(false)) }
            }
            softly { checkThat("FirstAssert", true, equalTo(false)) }
        }
    }

    @Test
    fun `actual can be null`() {
        softly {
            checkThat("Null check", null, isEmptyOrNullString())
        }
    }
}