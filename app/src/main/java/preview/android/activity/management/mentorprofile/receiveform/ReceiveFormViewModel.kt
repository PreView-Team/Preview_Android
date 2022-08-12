package preview.android.activity.management.mentorprofile.receiveform

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.activity.api.dto.FormResponse
import preview.android.activity.api.dto.ReceiveFormDetailResponse
import preview.android.activity.util.MutableListLiveData
import preview.android.activity.util.filtJsonArray
import preview.android.model.AlarmObject
import preview.android.model.ReceiveFormThumbnail
import preview.android.repository.AlarmRepository
import preview.android.repository.ChatRepository
import preview.android.repository.FormRepository
import javax.inject.Inject

@HiltViewModel
class ReceiveFormViewModel @Inject constructor(
    private val formRepository: FormRepository,
    private val chatRepository: ChatRepository,
    private val alarmRepository: AlarmRepository
) : BaseViewModel() {

    private val _receiveFormThumbnailList = MutableListLiveData<ReceiveFormThumbnail>()
    val receiveFormThumbnailList: LiveData<List<ReceiveFormThumbnail>> get() = _receiveFormThumbnailList

    private val _receiveFormDetail = MutableLiveData<ReceiveFormDetailResponse>()
    val receiveFormDetail: LiveData<ReceiveFormDetailResponse> get() = _receiveFormDetail

    private val _aceeptFormResponse = MutableLiveData<String>()
    val aceeptFormResponse: LiveData<String> get() = _aceeptFormResponse

    private val _createRoomResponse = MutableLiveData<String>()
    val createRoomResponse: LiveData<String> get() = _createRoomResponse

    fun updateReceiveThumbnailList(list: List<ReceiveFormThumbnail>) {
        _receiveFormThumbnailList.clear()
        _receiveFormThumbnailList.addAll(list)
    }

    fun setReceiveFormDetail(receiveFormDetailResponse: ReceiveFormDetailResponse) {
        _receiveFormDetail.value = receiveFormDetailResponse
    }

    fun getReceiveForms(token: String) = viewModelScope.launch {
        formRepository.getReceiveForms(token).collect { response ->
            if(response !is String){
                //Log.e("response", response.toString())
                updateReceiveThumbnailList(filtJsonArray(response as JsonArray))
            }
        }
    }

    fun getReceiveFormDetail(token: String, formId: Int) = viewModelScope.launch {
        formRepository.getReceiveFormDetail(token, formId).collect { response ->
            Log.e("getReceiveFormDetail", response.toString())
            setReceiveFormDetail(response as ReceiveFormDetailResponse)
        }
    }

    fun aceeptForm(token: String, formId: Int) = viewModelScope.launch {
        formRepository.acceptForm(token, formId).collect { response ->
            _aceeptFormResponse.value = (response as FormResponse).status
        }
    }

    fun refuseForm(token: String, formId: Int) = viewModelScope.launch {
        formRepository.refuseForm(token, formId).collect { response ->
           //Log.e("acceptForm", response.toString())
            Log.e("aceeptForm", response.toString())
            //_aceeptFormResponse.value = response
        }
    }

    fun createRoom(menteeNickname : String, menteeFCMToken : String) = viewModelScope.launch{
        chatRepository.createChatRoom(menteeNickname, menteeFCMToken).collect {
            Log.e("createRoom reponse", it)
            _createRoomResponse.value = it
        }
    }

    fun addAlarm(nickname : String, alarmObject: AlarmObject) = viewModelScope.launch{
        runCatching {
            alarmRepository.addAlarm(nickname,  alarmObject)
        }.onSuccess {
            Log.e("rfviewmodel addAlarm", "success")
        }.onFailure {
            Log.e("addAlarm", "fail" + it.message.toString())
        }
    }
}