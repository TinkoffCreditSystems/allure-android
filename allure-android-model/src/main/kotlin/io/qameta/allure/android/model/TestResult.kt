package io.qameta.allure.android.model

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.HashSet

/**
 * @author Badya on 14.04.2017.
 */
class TestResult(
        @SerializedName("uuid") val uuid: String = UUID.randomUUID().toString(),
        name: String? = null,
        @SerializedName("historyId") var historyId: String? = null,
        @SerializedName("testCaseId") var testCaseId: String? = null,
        @SerializedName("rerunOf") var rerunOf: String? = null,
        @SerializedName("fullName") var fullName: String? = null,
        @SerializedName("labels") var labels: Set<Label> = HashSet(),
        @SerializedName("links") var links: Set<Link> = HashSet()
) : ExecutableItem(name = name)