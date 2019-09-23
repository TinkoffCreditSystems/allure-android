package io.qameta.allure.android.model

import com.google.gson.annotations.SerializedName

/**
 * @author Badya on 14.04.2017.
 */
enum class Stage(val s: String) {
    @SerializedName("scheduled")
    SCHEDULED("scheduled"),
    @SerializedName("running")
    RUNNING("scheduled"),
    @SerializedName("finished")
    FINISHED("scheduled"),
    @SerializedName("pending")
    PENDING("scheduled"),
    @SerializedName("interrupted")
    INTERRUPTED("scheduled");
}