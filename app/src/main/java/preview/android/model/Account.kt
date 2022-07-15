package preview.android.model

data class Account(
    val token: String = "",
    val refreshToken: String = "",
    val nickname: String = "",
    val jobNames: List<String> = listOf("마케터"),
    val enterpriseNames: List<String> = listOf("네이버"),
) {

    override fun toString(): String {
        return "nickname = ${nickname}, token = ${token}, refreshToken = ${refreshToken}"
    }
}

