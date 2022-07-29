package preview.android.activity.management.chat

import androidx.lifecycle.LiveData
import preview.android.BaseViewModel
import preview.android.activity.util.MutableListLiveData
import preview.android.model.Message
import preview.android.model.ReceiveFormThumbnail

class ChatViewModel : BaseViewModel() {

    private val _messageList = MutableListLiveData<Message>()
    val messageList: LiveData<List<Message>> get() = _messageList

    private val _chatRoomList = MutableListLiveData<String>()
    val chatRoomList : LiveData<List<String>> get() = _chatRoomList

    fun updateMessageList(list : List<Message>){
        _messageList.clear()
        _messageList.addAll(list)
    }
    fun updateChatRoomList(list : List<String>){
        _chatRoomList.clear()
        _chatRoomList.addAll(list)
    }
}