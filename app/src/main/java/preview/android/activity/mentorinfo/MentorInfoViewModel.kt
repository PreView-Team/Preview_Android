package preview.android.activity.mentorinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.model.Form
import preview.android.repository.MentorRepository
import javax.inject.Inject

@HiltViewModel
class MentorInfoViewModel @Inject constructor(
    private val mentorRepository: MentorRepository
) : BaseViewModel() {
    private val _response = MutableLiveData<String>()
    val response: LiveData<String> get() = _response

    fun sendForm(token: String, form: Form) = viewModelScope.launch {
        mentorRepository.sendForm(token, form).collect{ response ->
            Log.e("sendForm", response.toString())
            _response.value = response.toString()
        }
    }

    fun getPostDetail(token: String, postId: Int) = viewModelScope.launch {
        mentorRepository.getPostDetail(token, postId).collect { response ->
            Log.e("getPostDetail", response.toString())
        }
    }
}