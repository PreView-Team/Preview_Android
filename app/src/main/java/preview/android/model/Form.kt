package preview.android.model

data class Form(
    val postId: Int = 0,
    val name: String = "",
    val phoneNumber: String = "",
    val contents : String = "",
    val local : String = "",
    val fcmToken :String = "",
    val jobNames: String ="",
)
