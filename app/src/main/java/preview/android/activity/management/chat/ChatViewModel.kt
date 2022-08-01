package preview.android.activity.management.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.activity.util.MutableListLiveData
import preview.android.model.Message
import preview.android.model.ReceiveFormThumbnail

class ChatViewModel : BaseViewModel() {

    private val _messageList = MutableStateFlow<List<Message>>(listOf())
    val messageList = _messageList.asStateFlow()

    private val _chatRoomList = MutableListLiveData<String>()
    val chatRoomList: LiveData<List<String>> get() = _chatRoomList

    fun updateMessageList(list: List<Message>) = viewModelScope.launch {
        _messageList.emit(list)
    }

    fun updateChatRoomList(list: List<String>) {
        _chatRoomList.clear()
        _chatRoomList.addAll(list)
    }
}