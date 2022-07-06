package preview.android.model

data class Account(
    val accessToken: String = "",
    val refreshToken: String = "",
    val nickname: String = "",
    val jobNames: List<String> = listOf("마케팅"),
    val enterpriseNames: List<String> = listOf("네이버"),
) {

    override fun toString(): String {
        return "nickname = ${nickname}, accessToken = ${accessToken}, refreshToken = ${refreshToken}"
    }
}

