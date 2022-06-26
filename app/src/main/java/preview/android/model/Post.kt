package preview.android.model

data class Post(
    val title : String = "",
    val content : String = "",
    val writer : String = "",
    val createDate : String = "",
    val bookmarks : Int = 0,
    val comments : Int = 0,
    val likes : Int = 0
)
