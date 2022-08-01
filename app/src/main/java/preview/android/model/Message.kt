package preview.android.model

data class Message(
    val nickname: String = "",
    val message: String = "",
    val count : Int,
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "nickname" to nickname,
            "message" to message
        )
    }
}