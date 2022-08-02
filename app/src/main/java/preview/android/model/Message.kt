package preview.android.model

data class Message(
    val nickname: String = "",
    val message: String = "",
    val count : Int,
    val mentorToken : String ="",
    val menteeToken : String ="",
)