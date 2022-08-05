package preview.android.model

data class EditMentorInfo(
    val nickname :String = "",
    val contents : String = "",
    val jobDtoSet : List<String> = listOf()
)
