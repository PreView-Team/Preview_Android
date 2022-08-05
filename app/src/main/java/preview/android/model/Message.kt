package preview.android.model

import preview.android.activity.util.getCurrentTime
import java.io.Serializable

data class Message(
    val nickname: String = "",
    val message: String = "",
    val count : Int,
    val mentorToken : String ="",
    val menteeToken : String ="",
    val time : String = getCurrentTime()
) : Serializable