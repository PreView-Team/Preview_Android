package preview.android.activity.management.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.activity.util.MutableListLiveData
import preview.android.activity.util.filtJsonArray
import preview.android.data.AccountStore
import preview.android.model.AlarmObject
import preview.android.model.Message
import preview.android.model.SendFormThumbnail
import preview.android.repository.AlarmRepository
import preview.android.repository.ChatRepository
import preview.android.repository.FormRepository
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val formRepository: FormRepository,
    private val chatRepository: ChatRepository,
    private val alarmRepository: AlarmRepository
) : BaseViewModel() {


    private val _messageMap=  MutableListLiveData<Map<String, List<Message>>>()
    val messageMap :LiveData<List<Map<String, List<Message>>>> get() = _messageMap

    private val _messageList = MutableStateFlow<List<Message>>(listOf())
    val messageList = _messageList.asStateFlow()


    private val _chatRoomList = MutableListLiveData<String>()
    val chatRoomList: LiveData<List<String>> get() = _chatRoomList

    private val _mentorChatList = MutableListLiveData<String>()
    val mentorChatList: LiveData<List<String>> get() = _mentorChatList

    private val _isFinishReadChat = MutableLiveData<Boolean>()
    val isFinishReadChat :LiveData<Boolean> get() = _isFinishReadChat

    private val _sendFormThumbnailList = MutableListLiveData<SendFormThumbnail>()
    val sendFormThumbnailList: LiveData<List<SendFormThumbnail>> get() = _sendFormThumbnailList


    fun addMessageMap(map: Map<String, List<Message>>) = viewModelScope.launch {
        _messageMap.add(map)
    }

    fun clearMessageMap(){
        _messageMap.clear()
    }

    fun updateMessageList(list: List<Message>) = viewModelScope.launch {
        _messageList.emit(list)
    }

    fun updateChatRoomList(list: List<String>) {
        _chatRoomList.clear()
        _chatRoomList.addAll(list)
    }

    fun addMentorChatList(list: List<String>) {
        _mentorChatList.addAll(list)
    }

    fun addChatRoomList(chatRoomName: String) {
        _chatRoomList.add(chatRoomName)
    }

    // 멘토의 하위 멘티 닉네임들을 읽어옴
    fun readChatRoomList(mentorNickname: String) = viewModelScope.launch {
        Log.e("readChatRoomList","!!")
        chatRepository.readMentorsChatRoom(mentorNickname).collect { list ->
            list.forEach { menteeNickname ->
                readChatList(mentorNickname, menteeNickname)
            }

        }
    }

    fun readChatList(mentorNickname: String, menteeNickname: String) = viewModelScope.launch {
        chatRepository.readChat(mentorNickname, menteeNickname).collect { list ->
            Log.e("readChatList?", list.toString())
            val map = mutableMapOf<String, List<Message>>()
            if (mentorNickname == AccountStore.mentorNickname.value) {
                map.put(menteeNickname + " 멘티", list)
            } else {
                map.put(mentorNickname + " 멘토", list)
            }
            addMessageMap(map)
            _isFinishReadChat.value = true

        }
    }

    fun readMessageList(mentorNickname: String, menteeNickname: String) = viewModelScope.launch {
        chatRepository.readChat(mentorNickname, menteeNickname).collect { list ->
            _messageList.emit(list)
        }
    }
    fun sendChat(mentorNickname: String, menteeNickname: String, message: Message, count: Int) =
        viewModelScope.launch {
            chatRepository.sendChat(mentorNickname, menteeNickname, message, count).collect {
                //Log.e("response", it)
            }
        }

    fun sendNotice(token: String, otherNickname: String) = viewModelScope.launch(Dispatchers.IO) {
        runCatching {
            alarmRepository.sendNotice(token, otherNickname)
        }.onSuccess {
            //Log.e("sendNotice", it.toString())
        }.onFailure {
            // Log.e("sendNotice", it.toString())
        }
    }

    fun addAlarm(otherNickname: String, alarmObject: AlarmObject) = viewModelScope.launch {
        runCatching {
            alarmRepository.addAlarm(otherNickname, alarmObject)
        }.onSuccess {
            //Log.e("chatviewmodel addAlarm", "success")
        }.onFailure {
            //Log.e("addAlarm", "fail" + it.message.toString())
        }
    }

    fun updateSendFormThumbnailList(list: List<SendFormThumbnail>) {
        _sendFormThumbnailList.clear()
        _sendFormThumbnailList.addAll(list)
    }

    fun clearSendFormThumbnailList() {
        _sendFormThumbnailList.clear()
    }

    fun getSendForms(token: String) = viewModelScope.launch {
        Log.e("getSendForms","!!")
        formRepository.getSendForms(token).collect { response ->
            updateSendFormThumbnailList(filtJsonArray(response as JsonArray))
        }
    }

}