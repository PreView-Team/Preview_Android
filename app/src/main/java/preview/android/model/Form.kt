package preview.android.model

data class Form(
    val postId: Int = 0,
    val name: String = "",
    val phoneNumber: String = "",
    val jobNames: List<Review> = listOf(),
    val contents : String = "",
)
