package preview.android.model

data class Mentor(
    val nickname : String = "",
    val tag : String = "",
    val title : String = "",
    val content : String = "",
    val likes : Int = 0,
    val comments : Int = 0
)