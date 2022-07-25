package preview.android.model

import java.io.Serializable

data class MentorPost(
    val title : String ="",
    val contents : String = "",
    val subTitle : String = "",
    val categoryId : Int = 1,
) : Serializable