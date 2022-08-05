package preview.android.activity.api.dto

data class GetMentorInfoResponse(
    val nickname : String = "",
    val contents : String = "",
    val jobNames : List<String> = listOf()
)
