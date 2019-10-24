package io.qameta.allure.android.model

import com.google.gson.annotations.SerializedName
import org.junit.AssumptionViolatedException

/**
 * @author Badya on 14.04.2017.
 */
enum class Status(val s: String) {
    @SerializedName("failed")
    FAILED("failed"),
    @SerializedName("broken")
    BROKEN("broken"),
    @SerializedName("passed")
    PASSED("passed"),
    @SerializedName("skipped")
    SKIPPED("skipped"),
    @SerializedName("unknown")
    UNKNOWN("unknown");

    companion object {
        @JvmStatic
        fun fromThrowable(e: Throwable?): Status {
            when (e) {
                is AssertionError -> return FAILED
                is AssumptionViolatedException -> return SKIPPED
                else -> return BROKEN
            }
        }
    }
}

