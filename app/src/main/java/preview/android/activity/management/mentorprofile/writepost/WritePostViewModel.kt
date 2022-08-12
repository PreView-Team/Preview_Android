package preview.android.activity.management.mentorprofile.writepost

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONArray
import preview.android.BaseViewModel
import preview.android.activity.util.MutableListLiveData
import preview.android.activity.util.filtJsonArray
import preview.android.model.*
import preview.android.repository.MentorRepository
import javax.inject.Inject

@HiltViewModel
class WritePostViewModel @Inject constructor(
    private val mentorRepository: MentorRepository
) : BaseViewModel() {


    private val _receivePostThumbnailList = MutableListLiveData<ReceivePost>()
    val receivePostThumbnailList: LiveData<List<ReceivePost>> get() = _receivePostThumbnailList

    private val _writePostDetail = MutableLiveData<ReceiveWritePost>()
    val writePostDetail: LiveData<ReceiveWritePost> get() = _writePostDetail
    fun updateReceivePostThumbnailList(list: List<ReceivePost>) {
        _receivePostThumbnailList.clear()
        _receivePostThumbnailList.addAll(list)
    }

    fun getWritePosts(token: String) = viewModelScope.launch {
        mentorRepository.getWritePosts(token).collect { response ->
            Log.e("getWritePosts", response.toString())
            updateReceivePostThumbnailList(filtJsonArray(response as JsonArray))
        }
    }

    fun getWritePostDetail(token: String, postId: Int) = viewModelScope.launch {
        mentorRepository.getWritePostDetail(token, postId).collect { postDetail ->
            _writePostDetail.value = postDetail as ReceiveWritePost
        }
    }

    fun editWritePostDetail(token: String, editPost: EditPost) = viewModelScope.launch {
        mentorRepository.editPost(token, editPost).collect { response ->
            Log.e("editWritePostDetail", response.toString())

        }
    }

    fun deleteWritePostDetail(token: String, postId: Int) = viewModelScope.launch {
        mentorRepository.deletePost(token, postId).collect { response ->
            Log.e("editWritePostDetail", response.toString())

        }
    }
}