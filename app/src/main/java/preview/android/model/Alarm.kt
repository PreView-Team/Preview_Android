package preview.android.model

data class Alarm(
    val title : String,
    val content : String,
    val time : String,
    val icon : String = "no"
)
