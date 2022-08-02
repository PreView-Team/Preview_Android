package preview.android.activity.management.sendform

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.activity.api.dto.FormDetailResponse
import preview.android.activity.util.MutableListLiveData
import preview.android.activity.util.filtJsonArray
import preview.android.model.EditForm
import preview.android.model.SendFormThumbnail
import preview.android.repository.FormRepository
import preview.android.repository.MentorRepository
import javax.inject.Inject

@HiltViewModel
class SendFormViewModel @Inject constructor(
    private val formRepository: FormRepository
) : BaseViewModel() {

    private val _formThumbnailList = MutableListLiveData<SendFormThumbnail>()
    val sendFormThumbnailList: LiveData<List<SendFormThumbnail>> get() = _formThumbnailList

    private val _formDetail = MutableLiveData<FormDetailResponse>()
    val formDetail: LiveData<FormDetailResponse> get() = _formDetail

    fun setFormDetail(response: FormDetailResponse) {
        _formDetail.value = response
    }

    fun updateFormThumbnailList(list: List<SendFormThumbnail>) {
        _formThumbnailList.clear()
        _formThumbnailList.addAll(list)
    }

    fun getSendForms(token: String) = viewModelScope.launch {
        Log.e("getReceiveForms", "!!")
        formRepository.getSendForms(token).collect { response ->
            updateFormThumbnailList(filtJsonArray(response as JsonArray))
        }
    }

    fun getFormDetail(token: String, formId: Int) = viewModelScope.launch {
        formRepository.getFormDetail(token, formId).collect { response ->
            Log.e("getFormDetail", "!!")
            Log.e("response", response.toString())
            // response 처리
            setFormDetail(response as FormDetailResponse)
        }
    }

    fun deleteForm(token: String, formId: Int) = viewModelScope.launch {
        formRepository.deleteForm(token, formId).collect { response ->
            Log.e("deleteForm response", response.toString())
        }
    }

    fun editForm(token: String, formId: Int, editForm: EditForm) = viewModelScope.launch {
        formRepository.editForm(token, formId, editForm).collect{
                response ->
            Log.e("editForm response", response.toString())
        }
    }
}