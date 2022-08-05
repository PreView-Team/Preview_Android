package preview.android.model

data class Account(
    val kakaoAccessToken: String = "",
//    val refreshToken: String = "",
    val nickname: String = "",
    val jobNames: List<String> = listOf("프로그래밍"),
) {

    override fun toString(): String {
        return "nickname = ${nickname}, kakaoAccessToken = ${kakaoAccessToken}, jobNames =${jobNames} "
    }
}
