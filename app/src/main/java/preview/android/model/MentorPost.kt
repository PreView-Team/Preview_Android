package preview.android.model

import java.io.Serializable

data class MentorPost(
    val title : String ="",
    val subTitle : String = "",
    val contents : String = "",
    val categortId : Int = 1,
    val kakaoId : Int = 0,
) : Serializable