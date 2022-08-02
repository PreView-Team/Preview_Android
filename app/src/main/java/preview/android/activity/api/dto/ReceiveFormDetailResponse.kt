package preview.android.activity.api.dto

import preview.android.model.JobName

data class ReceiveFormDetailResponse(
    val status : String = "",
    val createTime : String = "",
    val username : String = "",
    val phoneNumber : String = "",
    val jobNames : List<JobName> =  listOf(),
    val contents : String = "",
)
