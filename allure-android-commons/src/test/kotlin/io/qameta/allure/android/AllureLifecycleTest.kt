package io.qameta.allure.android

import io.qameta.allure.android.AllureCommonLifecycle
import io.qameta.allure.android.AllureLifecycle
import io.qameta.allure.android.AllureStorage
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import io.qameta.allure.android.model.Stage
import io.qameta.allure.android.model.StepResult
import io.qameta.allure.android.model.TestResult
import io.qameta.allure.android.model.TestResultContainer
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * @author Badya on 02.06.2017.
 */
class AllureLifecycleTest {
    lateinit var lifecycle: AllureLifecycle
    lateinit var testContainer: TestResultContainer
    lateinit var testResult: TestResult
    lateinit var stepResult: StepResult

    @Before
    fun setUp() {
        lifecycle = AllureCommonLifecycle
        testContainer = TestResultContainer()
        testResult = TestResult()
        stepResult = StepResult()
    }

    @After
    fun tearDown() {
        AllureStorage.clearStepContext()
    }

    @Test
    fun `startTestContainer inserted`() {
        lifecycle.startTestContainer(testContainer)
        Assert.assertSame("Container wasn't added to lifecycle", testContainer, AllureStorage.getContainer(testContainer.uuid))
    }

    @Test
    fun `startTestContainer inside another Container hasChild`() {
        val child = TestResultContainer()
        lifecycle.startTestContainer(testContainer)
        lifecycle.startTestContainer(testContainer.uuid, child)
        assertTrue("TestContainer wasn't added to TestContainer") { child.uuid in testContainer.children }
    }

    @Test
    fun `scheduleTestCase scheduled`() {
        lifecycle.scheduleTestCase(testResult)
        assertEquals(Stage.SCHEDULED, testResult.stage, "TestResult wasn't SCHEDULED")
    }

    @Test
    fun `scheduleTestCase container hasChild`() {
        lifecycle.startTestContainer(testContainer)
        lifecycle.scheduleTestCase(testContainer.uuid, testResult)

        assertTrue("TestResult wasn't added to TestContainer") { testResult.uuid in testContainer.children }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `startStep withoutTest exception`() {
        lifecycle.startStep(stepResult)
    }

    @Test
    fun `startStep push to stepContext`() {
        lifecycle.scheduleTestCase(testResult)
        lifecycle.startTestCase(testResult.uuid)
        lifecycle.startStep(stepResult)
        assertEquals(stepResult.uuid, AllureStorage.getCurrentStep(), "Step not on top of stepContext")
    }

    @Test
    fun `stopStep removes from stepContext`() {
        lifecycle.scheduleTestCase(testResult)
        lifecycle.startTestCase(testResult.uuid)
        lifecycle.startStep(stepResult)
        lifecycle.stopStep()
        assertNotEquals(stepResult.uuid, AllureStorage.getCurrentStep(), "Step wasn't removed")
    }

    @Test
    fun `several Test inContainer`() {
        val testResult2 = TestResult()
        with(lifecycle) {
            startTestContainer(testContainer)
            scheduleTestCase(testContainer.uuid, testResult)
            startTestCase(testResult.uuid)
            stopTestCase()
            scheduleTestCase(testContainer.uuid, testResult2)
            startTestCase(testResult2.uuid)
            stopTestCase()
        }

        assertTrue("First TestResult not in container") { testResult.uuid in testContainer.children }
        assertTrue("Second TestResult not in container") { testResult2.uuid in testContainer.children }
    }
}