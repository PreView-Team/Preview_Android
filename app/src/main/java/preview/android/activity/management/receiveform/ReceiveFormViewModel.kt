package preview.android.activity.management.receiveform

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.activity.api.dto.FormDetailResponse
import preview.android.activity.api.dto.ReceiveFormDetailResponse
import preview.android.activity.util.MutableListLiveData
import preview.android.activity.util.filtJsonArray
import preview.android.model.ReceiveFormThumbnail
import preview.android.model.SendFormThumbnail
import preview.android.repository.FormRepository
import preview.android.repository.MentorRepository
import javax.inject.Inject

@HiltViewModel
class ReceiveFormViewModel @Inject constructor(
    private val formRepository: FormRepository
) : BaseViewModel() {

    private val _receiveFormThumbnailList = MutableListLiveData<ReceiveFormThumbnail>()
    val receiveFormThumbnailList: LiveData<List<ReceiveFormThumbnail>> get() = _receiveFormThumbnailList

    private val _receiveFormDetail = MutableLiveData<ReceiveFormDetailResponse>()
    val receiveFormDetail: LiveData<ReceiveFormDetailResponse> get() = _receiveFormDetail

    fun updateReceiveThumbnailList(list: List<ReceiveFormThumbnail>) {
        _receiveFormThumbnailList.clear()
        _receiveFormThumbnailList.addAll(list)
    }

    fun setReceiveFormDetail(receiveFormDetailResponse: ReceiveFormDetailResponse) {
        _receiveFormDetail.value = receiveFormDetailResponse
    }

    fun getReceiveForms(token: String) = viewModelScope.launch {
        formRepository.getReceiveForms(token).collect { response ->

            Log.e("response", response.toString())
            updateReceiveThumbnailList(filtJsonArray(response as JsonArray))
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
            Log.e("acceptForm", response.toString())
        }
    }

    fun refuseForm(token: String, formId: Int) = viewModelScope.launch {
        formRepository.refuseForm(token, formId).collect { response ->
            Log.e("acceptForm", response.toString())
        }
    }

}