package preview.android.model

import java.io.Serializable
import java.sql.Timestamp

data class MentorPost(
    val postId: Int = 1,
    val nickname: String = "",
    val category: String = "마케터",
    val title : String = "",
    val createdAt : String = "",
    val updatedAt : String = "",
    val introduce : String = "",
    val contents: String = "",
    val like : Boolean = false,
    val likeCount : Int = 0,
    val grade : Double = 0.0
  //  val review : Boolean = false,
  //  val reviewCount : Int = 0,
) : Serializable