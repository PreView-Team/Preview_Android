package preview.android.activity.management.receiveform

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.activity.util.MutableListLiveData
import preview.android.activity.util.filtJsonArray
import preview.android.model.ReceiveFormThumbnail
import preview.android.model.SendFormThumbnail
import preview.android.repository.MentorRepository
import javax.inject.Inject

@HiltViewModel
class ReceiveFormViewModel @Inject constructor(
    private val mentorRepository: MentorRepository
) : BaseViewModel() {

    private val _receiveFormThumbnailList = MutableListLiveData<ReceiveFormThumbnail>()
    val receiveFormThumbnailList: LiveData<List<ReceiveFormThumbnail>> get() = _receiveFormThumbnailList

    fun updateReceiveThumbnailList(list: List<ReceiveFormThumbnail>) {
        _receiveFormThumbnailList.clear()
        _receiveFormThumbnailList.addAll(list)
    }

    fun getReceiveForms(token: String) = viewModelScope.launch {
        mentorRepository.getReceiveForms(token).collect { response ->

            Log.e("response", response.toString())
            updateReceiveThumbnailList(filtJsonArray(response as JsonArray))
        }
    }

    fun getReceiveFormDetail(token: String, formId : Int)= viewModelScope.launch {
        mentorRepository.getReceiveFormDetail(token, formId).collect { response ->
            Log.e("getReceiveFormDetail", response.toString())
        }
    }
}