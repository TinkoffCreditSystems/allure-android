package io.qameta.allure.android.model

import com.google.gson.annotations.SerializedName

/**
 * @author Badya on 14.04.2017.
 */
data class Label(
        @SerializedName("name") var name: String? = null,
        @SerializedName("value") var value: String? = null)