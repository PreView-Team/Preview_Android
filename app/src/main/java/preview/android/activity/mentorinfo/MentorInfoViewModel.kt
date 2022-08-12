package preview.android.activity.mentorinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.activity.api.dto.PostDetailResponse
import preview.android.model.Form
import preview.android.repository.FormRepository
import preview.android.repository.MentorRepository
import javax.inject.Inject

@HiltViewModel
class MentorInfoViewModel @Inject constructor(
    private val mentorRepository: MentorRepository,
    private val formRepository: FormRepository
) : BaseViewModel() {

    private val _response = MutableLiveData<String>()
    val response: LiveData<String> get() = _response

    private val _postDetail = MutableLiveData<PostDetailResponse>()
    val postDetail: LiveData<PostDetailResponse> get() = _postDetail

    fun sendForm(token: String, form: Form) = viewModelScope.launch {
        formRepository.sendForm(token, form).collect { response ->
            Log.e("sendForm response", response.toString())
            _response.value = response.toString()
        }
    }

    fun getPostDetail(token: String, postId: Int, page: Int, size: Int, sort: String) =
        viewModelScope.launch {
            mentorRepository.getPostDetail(token, postId, page, size, sort).collect { response ->
                if (response != null) {
                    _postDetail.value = response as PostDetailResponse
                }
            }
        }

    fun getPostDetail(token: String, postId: Int) =
        viewModelScope.launch {
            mentorRepository.getPostDetail(token, postId).collect { response ->
                if (response != null) {
                    _postDetail.value = response as PostDetailResponse
                }
            }
        }

}