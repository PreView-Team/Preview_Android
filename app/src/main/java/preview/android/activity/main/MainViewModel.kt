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

    private val _checkNewMentorListEnd = MutableLiveData<String>()
    val checkNewMentorListEnd: LiveData<String> get() = _checkNewMentorListEnd

    private val _checkRecommendMentorListEnd = MutableLiveData<String>()
    val checkRecommendMentorListEnd: LiveData<String> get() = _checkRecommendMentorListEnd

    private val _checkNewMentorThumbnailListEnd = MutableLiveData<String>()
    val checkNewMentorThumbnailListEnd: LiveData<String> get() = _checkNewMentorThumbnailListEnd

    private val _checkRecommendMentorThumbnailListEnd = MutableLiveData<String>()
    val checkRecommendMentorThumbnailListEnd: LiveData<String> get() = _checkRecommendMentorThumbnailListEnd

    private val _sendWritingResponse = MutableLiveData<String>()
     val sendWritingResponse : LiveData<String> get() = _sendWritingResponse

    fun updateNewMentorThumbnailList(list: List<MentorThumbnail>) {

        _newMentorThumbnailList.addAll(list)
    }
    fun clearNewMentorThumbnailList() {
        _newMentorThumbnailList.clear()

    }
    fun updateRecommendMentorThumbnailList(list: List<MentorThumbnail>) {
        _recommendMentorThumbnailList.clear()
        _recommendMentorThumbnailList.addAll(list)
    }
    fun clearRecommendMentorThumbnailList() {
        _recommendMentorThumbnailList.clear()
    }
    fun updateNewMentorPostList(list: List<MentorPost>) {
        // _newMentorPostList.clear()
        _newMentorPostList.addAll(list)
    }

    fun updateRecommendMentorPostList(list: List<MentorPost>) {
        //_recommendMentorPostList.clear()
        _recommendMentorPostList.addAll(list)
    }

    fun updateFragmentState(fragmentState: FragmentState) {
        _fragmentState.value = fragmentState
    }

    fun clearNewMentorPostList() {
        _newMentorPostList.clear()
    }
    fun clearRecommendMentorPostList() {
        _recommendMentorPostList.clear()

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
                    val list : List<MentorThumbnail> = filtJsonArray(response as JsonArray)
                    if (list.isEmpty()) {
                        _checkNewMentorThumbnailListEnd.value = "isEmpty"
                    } else {
                        _checkNewMentorThumbnailListEnd.value = "isNotEmpty"
                        updateNewMentorThumbnailList(list)
                    }
                }
        }

    fun getRecommendMentorThumbnailList(token: String, page: Int, size: Int, sort: String) =
        viewModelScope.launch {
            mentorRepository.getRecommendMentorThumbnailList(token, page, size, sort)
                .collect { response ->
                    val list : List<MentorThumbnail> = filtJsonArray(response as JsonArray)
                    if (list.isEmpty()) {
                        _checkRecommendMentorThumbnailListEnd.value = "isEmpty"
                    } else {
                        _checkRecommendMentorThumbnailListEnd.value = "isNotEmpty"
                        updateRecommendMentorThumbnailList(list)
                    }
                }
        }

//    fun getCategoryNewMentorPostList(token: String, categoryName: String) =
//        viewModelScope.launch {
//            mentorRepository.getCategoryNewMentorPostList(token, categoryName).collect { response ->
//                Log.e("new LIST", response.toString())
//                updateNewMentorPostList(filtJsonArray(response as JsonArray))
//            }
//        }

    fun getCategoryNewMentorPostList(
        token: String,
        categoryName: String,
        page: Int,
        size: Int,
        sort: String
    ) =
        viewModelScope.launch {
            mentorRepository.getCategoryNewMentorPostList(token, categoryName, page, size, sort)
                .collect { response ->
                    val mentorList: List<MentorPost> = filtJsonArray(response as JsonArray)
                    if (mentorList.isEmpty()) {
                        _checkNewMentorListEnd.value = "isEmpty"
                    } else {
                        _checkNewMentorListEnd.value = "isNotEmpty"
                        updateNewMentorPostList(mentorList)
                    }
                }
        }

    fun getCategoryRecommendMentorPostList(
        token: String,
        categoryName: String,
        page: Int,
        size: Int,
        sort: String
    ) =
        viewModelScope.launch {
            mentorRepository.getCategoryRecommendMentorPostList(
                token,
                categoryName,
                page,
                size,
                sort
            )
                .collect { response ->
                    Log.e("recommend LIST", response.toString())
                    val mentorList: List<MentorPost> = filtJsonArray(response as JsonArray)
                    if (mentorList.isEmpty()) {
                        _checkRecommendMentorListEnd.value = "isEmpty"
                    } else {
                        _checkRecommendMentorListEnd.value = "isNotEmpty"
                        updateRecommendMentorPostList(mentorList)
                    }
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

    fun searhMentors(token: String, keyword: String, category: String, page: Int, size: Int) =
        viewModelScope.launch {
            mentorRepository.searchMentors(token, keyword, category, page, size)
                .collect { response ->
                    updateNewMentorPostList(filtJsonArray(response as JsonArray))
                }
        }

    fun createAlarmList(myNickname: String) = viewModelScope.launch {
        runCatching {
            alarmRepository.createAlarmList(myNickname, AlarmObject())
        }.onSuccess {
            Log.e("createAlarmList", "success")
        }.onFailure {
            Log.e("createAlarmList", "fail" + it.message.toString())
        }
    }
}