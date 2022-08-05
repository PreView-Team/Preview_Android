package preview.android.activity.api.dto

data class GetUserInfoResponse(
    val nickname : String = "",
    val jobNames : List<String> = listOf(),
    val isMentored : Boolean = false
)
