package io.qameta.allure.android

import org.hamcrest.Matcher
import org.junit.Assert.assertThat
import org.junit.runners.model.MultipleFailureException
import io.qameta.allure.android.model.ExecutableItem
import io.qameta.allure.android.model.Status
import io.qameta.allure.android.model.StatusDetails
import io.qameta.allure.android.model.TestResult

/**
 * @author Badya on 01.06.2017.
 */

inline fun softly(block: SoftAssert.() -> Unit) {
    with(SoftAssert()) {
        block()
        verify()
    }
}

// todo: use lifecycle instead of direct storage access
class SoftAssert {
    private val errorsMap = mutableMapOf<String, MutableList<Throwable>>()

    @JvmOverloads
    fun <T> checkThat(reason: String = "", value: T, matcher: Matcher<T>) {
        try {
            assertThat(reason, value, matcher)
        } catch (t: Throwable) {
            addError(t)
        }
    }

    fun verify() {
        errorsMap.forEach { (uuid, errList) ->
            AllureStorage.get(uuid, ExecutableItem::class.java).apply {
                try {
                    MultipleFailureException.assertEmpty(errList)
                } catch (t: Throwable) {
                    status = Status.FAILED
                    statusDetails = StatusDetails.fromThrowable(t)
                }
            }
            AllureStorage.get(AllureStorage.getTest(), TestResult::class.java).apply { status = Status.FAILED }
        }
    }

    private fun addError(error: Throwable) {
        errorsMap.getOrPut(AllureStorage.getCurrentStep(), defaultValue = { mutableListOf() }).add(error)
    }
}