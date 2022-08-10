package preview.android.activity.management.menteechat

import android.util.Log
import androidx.lifecycle.LiveData
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
import preview.android.model.ChatRoom
import preview.android.model.Message
import preview.android.model.SendFormThumbnail
import preview.android.repository.AlarmRepository
import preview.android.repository.ChatRepository
import preview.android.repository.FormRepository
import javax.inject.Inject

@HiltViewModel
class MenteeChatViewModel @Inject constructor(
    private val formRepository: FormRepository,
    private val chatRepository: ChatRepository,
    private val alarmRepository: AlarmRepository
) : BaseViewModel() {

    private val _messageMap = MutableListLiveData<Map<String, List<Message>>>()
    val messageMap: LiveData<List<Map<String, List<Message>>>> get() = _messageMap

    private val _messageList = MutableStateFlow<List<Message>>(listOf())
    val messageList = _messageList.asStateFlow()



    private val _sendFormThumbnailList = MutableListLiveData<SendFormThumbnail>()
    val sendFormThumbnailList: LiveData<List<SendFormThumbnail>> get() = _sendFormThumbnailList


    fun addMessageMap(map: Map<String, List<Message>>) = viewModelScope.launch {
        _messageMap.add(map)
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

            //Log.e("Add map?", map.toString())
            filtMessageMap(map)

        }
    }

    fun filtMessageMap(map: Map<String, List<Message>>) {
        var isFiltered = false
        val messageMap = _messageMap.value?.toMutableList()
        messageMap?.forEach { mentorChatRoomMap -> // 멘토별 채팅방 맵
            if (map.keys.toString() == mentorChatRoomMap.keys.toString()) {

                // 비교를 위한 필터링
                var mapChatRoom = ChatRoom()
                var mentorChatRoom = ChatRoom()
                map.forEach { nickname, list ->
                    mapChatRoom = ChatRoom(nickname = nickname, chatList = list)
                }
                mentorChatRoomMap.forEach { nickname, list ->
                    mentorChatRoom = ChatRoom(nickname = nickname, chatList = list)
                }

                // 사이즈 비교
                if (mapChatRoom.chatList.size >= mentorChatRoom.chatList.size) {
                    _messageMap.add(map)
                    _messageMap.remove(mentorChatRoomMap)
                    isFiltered = true
                }
                Log.e("size 1", mapChatRoom.chatList.size.toString())
                Log.e("size 2", mentorChatRoom.chatList.size.toString())
            }
        }
        if (!isFiltered) {
            addMessageMap(map)
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

    fun sendNotice(token: String, myNickname: String, contents: String) =
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                alarmRepository.sendNotice(token, myNickname, contents)
            }.onSuccess {
                Log.e("sendNotice success", it.toString())
            }.onFailure {
                Log.e("sendNotice fail", it.toString())
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

    fun getSendForms(token: String) = viewModelScope.launch {
        Log.e("getSendForms","!!")
        formRepository.getSendForms(token).collect { response ->
            updateSendFormThumbnailList(filtJsonArray(response as JsonArray))
        }
    }

    fun acceptMentoring(mentorNickname: String, menteeNickname: String, count: Int, date: String) =
        viewModelScope.launch {
            chatRepository.acceptMentoring(mentorNickname, menteeNickname, count, date).collect {
                Log.e("acceptMentoring response", it)
            }
        }

}