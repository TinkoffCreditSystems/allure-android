package io.qameta.allure.android.model

import com.google.gson.annotations.SerializedName

/**
 * @author Badya on 14.04.2017.
 */
abstract class ExecutableItem(
        @SerializedName("name") open var name: String? = null,
        @SerializedName("start") open var start: Long? = null,
        @SerializedName("stop") open var stop: Long? = null,
        @SerializedName("description") open var description: String? = null,
        @SerializedName("descriptionHtml") open var descriptionHtml: String? = null,
        @SerializedName("stage") open var stage: Stage? = null,
        @SerializedName("status") override var status: Status? = null,
        @SerializedName("statusDetails") override var statusDetails: StatusDetails? = null,
        @SerializedName("steps") override var steps: MutableList<StepResult> = ArrayList(),
        @SerializedName("attachments") override var attachments: MutableList<Attachment> = ArrayList(),
        @SerializedName("parameters") override var parameters: MutableList<Parameter> = ArrayList()
) : WithSteps, WithAttachments, WithParameters, WithStatusDetails {
    fun calcStatus() {
        fun updateStatusInfo(item: ExecutableItem) {
            if (status != null && status in arrayOf(Status.BROKEN, Status.FAILED)) return
            status = item.status
            statusDetails = item.statusDetails
        }

        steps.forEach {
            it.calcStatus()
            when (it.status) {
                Status.BROKEN, Status.FAILED -> updateStatusInfo(it)
                else                         -> Unit
            }
        }
    }

}

