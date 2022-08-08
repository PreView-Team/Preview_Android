package preview.android.activity.api.dto

data class ReceiveFormDetailResponse(
    val status: String = "",
    val createTime: String = "",
    val username: String = "",
    val phoneNumber: String = "",
    val jobNames: String = "",
    val contents: String = "",
    val fcmToken: String = "",
    val local: String = "",
)
