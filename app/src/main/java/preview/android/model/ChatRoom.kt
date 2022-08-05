package preview.android.model

import java.io.Serializable

data class ChatRoom(
    val nickname : String = "",
    val chatList : List<Message> = listOf(),
    val date : String = ""
) : Serializable
