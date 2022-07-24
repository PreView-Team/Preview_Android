package preview.android.model

import java.io.Serializable

data class MentorPost(
    val nickname: String = "",
    val title : String ="",
    val contents : String = "",
    val tag : String = "",
    val like : String = "",
    val comment : String = "",
    val subTitle : String = "",
    val categortId : Int = 1,
    val kakaoId : Long = 0,
) : Serializable