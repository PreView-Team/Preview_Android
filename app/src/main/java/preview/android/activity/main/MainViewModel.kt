package preview.android.activity.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.JsonArray
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.activity.util.MutableListLiveData
import preview.android.activity.util.filtJsonArray
import preview.android.activity.util.toObjectNonNull
import preview.android.data.AccountStore
import preview.android.data.AlarmStore
import preview.android.model.*
import preview.android.repository.AlarmRepository
import preview.android.repository.MentorRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mentorRepository: MentorRepository,
    private val alarmRepository: AlarmRepository
) : BaseViewModel() {


    sealed class FragmentState {
        object newMentor : FragmentState()
        object recommendMentor : FragmentState()
        object home : FragmentState()
        object community : FragmentState()
        object setting : FragmentState()
    }

    private var _fragmentState = MutableLiveData<FragmentState>()
    val fragmentState: LiveData<FragmentState> get() = _fragmentState

    private var _writing = MutableLiveData<Writing>()
    val writing: LiveData<Writing> get() = _writing

    private val _token = MutableLiveData<String>("")
    val token: LiveData<String> get() = _token

    private val _newMentorThumbnailList = MutableListLiveData<MentorThumbnail>()
    val newMentorThumbnailList: LiveData<List<MentorThumbnail>> get() = _newMentorThumbnailList

    private val _recommendMentorThumbnailList = MutableListLiveData<MentorThumbnail>()
    val recommendMentorThumbnailList: LiveData<List<MentorThumbnail>> get() = _recommendMentorThumbnailList

    private val _newMentorPostList = MutableListLiveData<MentorPost>()
    val newMentorPostList: LiveData<List<MentorPost>> get() = _newMentorPostList

    private val _recommendMentorPostList = MutableListLiveData<MentorPost>()
    val recommendMentorPostList: LiveData<List<MentorPost>> get() = _recommendMentorPostList


    fun updateNewMentorThumbnailList(list: List<MentorThumbnail>) {
        _newMentorThumbnailList.clear()
        _newMentorThumbnailList.addAll(list)
    }

    fun updateRecommendMentorThumbnailList(list: List<MentorThumbnail>) {
        _recommendMentorThumbnailList.clear()
        _recommendMentorThumbnailList.addAll(list)
    }

    fun updateNewMentorPostList(list: List<MentorPost>) {
        _newMentorPostList.clear()
        _newMentorPostList.addAll(list)
    }

    fun updateRecommendMentorPostList(list: List<MentorPost>) {
        _recommendMentorPostList.clear()
        _recommendMentorPostList.addAll(list)
    }

    fun updateFragmentState(fragmentState: FragmentState) {
        _fragmentState.value = fragmentState
    }

    fun loadToken(): String {
        return _token.value!!
    }

    fun setToken(token: String) {
        _token.value = token
    }

    fun getNewMentorThumbnailList(token: String, page: Int, size: Int, sort: String) =
        viewModelScope.launch {
            mentorRepository.getNewMentorThumbnailList(token, page, size, sort)
                .collect { response ->
                    Log.e("getNewMentorThumbnailList", response.toString())
                    updateNewMentorThumbnailList(filtJsonArray(response as JsonArray))
                }
        }

    fun getRecommendMentorThumbnailList(token: String, page: Int, size: Int, sort: String) =
        viewModelScope.launch {
            mentorRepository.getRecommendMentorThumbnailList(token, page, size, sort)
                .collect { response ->
                    Log.e("getRecommendMentorThumbnailList", response.toString())
                    updateRecommendMentorThumbnailList(filtJsonArray(response as JsonArray))
                }
        }

    fun getCategoryNewMentorPostList(token: String, categoryName: String) =
        viewModelScope.launch {
            mentorRepository.getCategoryNewMentorPostList(token, categoryName).collect { response ->
                Log.e("new LIST", response.toString())
                updateNewMentorPostList(filtJsonArray(response as JsonArray))
            }
        }

    fun getCategoryRecommendMentorPostList(token: String, categoryName: String) =
        viewModelScope.launch {
            mentorRepository.getCategoryRecommendMentorPostList(token, categoryName)
                .collect { response ->
                    Log.e("recommend LIST", response.toString())
                    updateRecommendMentorPostList(filtJsonArray(response as JsonArray))
                }
        }

    fun setWriting(writing: Writing) {
        _writing.value = writing
    }


    fun sendWriting(token: String, writing: Writing) = viewModelScope.launch {
        mentorRepository.sendWriting(token, writing).collect { response ->
            Log.e("sendWriteMentorPost", response.toString())
        }
    }

    fun registMentor(token: String, kakaoId: Long) = viewModelScope.launch {
        mentorRepository.registMentor(token, kakaoId).collect { response ->
            Log.e("registMentor", response.toString())
        }
    }

    fun likePost(token: String, postId: Int) = viewModelScope.launch {
        mentorRepository.likePost(token, postId).collect { response ->
            Log.e("likePost", response.toString())
        }
    }

    fun unLikePost(token: String, postId: Int) = viewModelScope.launch {
        mentorRepository.unLikePost(token, postId).collect { response ->
            Log.e("unLikePost", response.toString())
        }
    }

    fun readAlarmList(myNickname: String) = viewModelScope.launch {
        runCatching {
            alarmRepository.readAlarmList(myNickname)
        }.onSuccess { value ->
            when (value) {
                is QuerySnapshot -> {
                    var list = mutableListOf<AlarmObject>()
                    value.documents.forEach { documentSnapshot ->
                        Log.e("value documents 1", documentSnapshot.data.toString())
                        Log.e("value documents 2", documentSnapshot.data!!.values.size.toString())
                        Log.e("value documents 3", documentSnapshot.data!!.values.toString())
                        list.add(documentSnapshot.toObjectNonNull())
                    }
                    if (list.size > 0) {
                        AlarmStore.updateAlarmList(list.get(0))
                    }
                }
                is Throwable -> {
                    Log.e("Error", "!!")
                }
            }
        }.onFailure {

        }
    }
}