package preview.android.model

import java.io.Serializable

data class MentorPost(
    val postId: Int = 1,
    val nickname: String = "",
    val categoryId: Int = 1,
    val introduce : String = "",
    val contents: String = "",
    val like : Boolean = false,
    val likeCount : Int = 0,
    val review : Boolean = false,
    val reviewCount : Int = 0,
) : Serializable