package ru.tinkoff.allure.android

import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.allure.step
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * @author Badya on 06.06.2017.
 */
@RunWith(AndroidJUnit4::class)
class AllureTest {
    @get:Rule
    val failshot = FailshotRule()

    @Test
    fun test1() = step("Step1") {
        assertTrue(true)
    }

    @Test(expected = AssertionError::class)
    fun test2() {
        step("Step1") {
            fail()
        }
    }
}