package preview.android.activity.api.dto

import preview.android.model.JobName

data class FormDetailResponse(
    val mentorNickname : String = "",
    val postId : Int = 0,
    val createTime : String = "",
    val name : String  = "",
    val phoneNumber : String = "",
    val jobNames : String= "",
    val status : String = "",
    val contents : String = "",
    val local : String =""
)
