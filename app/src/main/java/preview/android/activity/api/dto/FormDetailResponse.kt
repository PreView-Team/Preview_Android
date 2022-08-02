package preview.android.activity.api.dto

import preview.android.model.JobName

data class FormDetailResponse(
    val mentorNickname : String = "",
    val formId : Int = 0,
    val createTime : String = "",
    val name : String  = "",
    val phoneNumber : String = "",
    val jobNames : List<JobName> = listOf(),
    val status : String = "",
    val contents : String = ""
)
