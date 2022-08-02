package preview.android.activity.management.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.activity.util.MutableListLiveData
import preview.android.model.Message
import preview.android.model.ReceiveFormThumbnail
import preview.android.repository.ChatRepository
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : BaseViewModel() {

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

    fun readChatRoomList() = viewModelScope.launch {
        chatRepository.readChatRoom().collect { list ->
            Log.e("readChatRoomList", list.toString())
            updateChatRoomList(list)
        }
    }

    fun readChatList(nickname: String) = viewModelScope.launch {
        chatRepository.readChat(nickname).collect { list ->
            Log.e("readChatList", list.toString())
            updateMessageList(list)
        }
    }

    fun sendChat(nickname: String, message: Message, count: Int) = viewModelScope.launch {
        chatRepository.sendChat(nickname, message, count).collect {
            Log.e("response", it)
        }
    }
}