package preview.android.model

data class Message(
    val nickname: String = "",
    val message: String = "",
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "nickname" to nickname,
            "message" to message
        )
    }
}