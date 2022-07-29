package preview.android.activity.management.sendform

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.activity.util.MutableListLiveData
import preview.android.activity.util.filtJsonArray
import preview.android.model.SendFormThumbnail
import preview.android.repository.MentorRepository
import javax.inject.Inject

@HiltViewModel
class SendFormViewModel @Inject constructor(
    private val mentorRepository: MentorRepository
) : BaseViewModel() {

    private val _formThumbnailList = MutableListLiveData<SendFormThumbnail>()
    val sendFormThumbnailList: LiveData<List<SendFormThumbnail>> get() = _formThumbnailList

    fun updateFormThumbnailList(list: List<SendFormThumbnail>) {
        _formThumbnailList.clear()
        _formThumbnailList.addAll(list)
    }

    fun getSendForms(token: String) = viewModelScope.launch {
        Log.e("getReceiveForms", "!!")
        mentorRepository.getSendForms(token).collect { response ->
            updateFormThumbnailList(filtJsonArray(response as JsonArray))
        }
    }

    fun getFormDetail(token: String, formId: Int) = viewModelScope.launch {
        mentorRepository.getFormDetail(token, formId).collect { response ->
            Log.e("getFormDetail", "!!")
            Log.e("response", response.toString())
            // response 처리
        }
    }
}