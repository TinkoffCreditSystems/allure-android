package io.qameta.allure.android.model

import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Badya on 23.06.2017.
 */
class ExecutableItemTest {
    @Test
    fun `when no subSteps return status`() {
        val mainStep = StepResult().apply {
            status = Status.PASSED
            calcStatus()
        }
        assertEquals(Status.PASSED, mainStep.status)
    }

    @Test
    fun `when subStep FAILED should be FAILED`() {
        val failedSubStep = StepResult().apply { status = Status.FAILED }
        val mainStep = StepResult().apply {
            status = Status.PASSED
            steps.add(failedSubStep)
            calcStatus()
        }
        assertEquals(Status.FAILED, mainStep.status)
    }

    @Test
    fun `when subStep BROKEN should be BROKEN`() {
        val brokenSubStep = StepResult().apply { status = Status.BROKEN }
        val mainStep = StepResult().apply {
            status = Status.PASSED
            steps.add(brokenSubStep)
            calcStatus()
        }
        assertEquals(Status.BROKEN, mainStep.status)
    }

    @Test
    fun `when subSteps FAILED and BROKEN prefer first`() {
        val failedSubStep = StepResult().apply { status = Status.FAILED }
        val brokenSubStep = StepResult().apply { status = Status.BROKEN }
        val mainStep = StepResult().apply {
            status = Status.PASSED
            steps.addAll(arrayOf(failedSubStep, brokenSubStep))
            calcStatus()
        }

        assertEquals(Status.FAILED, mainStep.status)
    }

    @Test
    fun `when subStep FAILED in subStep should be FAILED`() {
        val failedSubStep = StepResult().apply { status = Status.FAILED }
        val passedSubStep = StepResult().apply {
            status = Status.PASSED
            steps.add(failedSubStep)
        }
        val mainStep = StepResult().apply {
            status = Status.PASSED
            steps.add(passedSubStep)
            calcStatus()
        }

        assertEquals(Status.FAILED, mainStep.status)
    }

    @Test
    fun `when subStep FAILED with status should have status`() {
        val testStatusDetails = StatusDetails.fromThrowable(AssertionError("TestError"))
        val failedSubStep = StepResult().apply {
            status = Status.FAILED
            statusDetails = testStatusDetails
        }
        val mainStep = StepResult().apply {
            status = Status.PASSED
            steps.add(failedSubStep)
            calcStatus()
        }

        assertEquals(testStatusDetails, mainStep.statusDetails)
    }

    @Test
    fun `when several fails prefer first`() {
        val testStatusDetails1 = StatusDetails.fromThrowable(AssertionError("TestError1"))
        val testStatusDetails2 = StatusDetails.fromThrowable(AssertionError("TestError2"))
        val failedSubStep1 = StepResult().apply {
            status = Status.FAILED
            statusDetails = testStatusDetails1
        }
        val brokenSubStep2 = StepResult().apply {
            status = Status.BROKEN
            statusDetails = testStatusDetails2
        }
        val mainStep = StepResult().apply {
            status = Status.PASSED
            steps.addAll(arrayOf(failedSubStep1, brokenSubStep2))
            calcStatus()
        }

        assertEquals(Status.FAILED, mainStep.status)
        assertEquals(testStatusDetails1, mainStep.statusDetails)
    }

}