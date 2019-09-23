package io.qameta.allure.android.model

import com.google.gson.annotations.SerializedName

/**
 * @author Badya on 14.04.2017.
 */
data class Attachment(
        @SerializedName("source") var source: String? = null,
        @SerializedName("name") var name: String? = null,
        @SerializedName("type") var type: String? = null)