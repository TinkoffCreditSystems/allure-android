package io.qameta.allure.android.model

import com.google.gson.annotations.SerializedName

/**
 * @author Badya on 14.04.2017.
 */
class Link(
        @SerializedName("name") var name: String? = null,
        @SerializedName("url") var url: String? = null,
        @SerializedName("type") var type: String? = null)