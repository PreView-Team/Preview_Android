package preview.android.model

import java.io.Serializable

data class MentorThumbnail(
    val nickname : String,
    val categoryName : String,
    val title : String,
    val likeCnt : Int,
    val commentCnt : Int
) : Serializable
