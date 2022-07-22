package preview.android.activity.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.data.MentorStore
import preview.android.model.MentorPost
import preview.android.repository.MentorRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mentorRepository: MentorRepository
) : BaseViewModel() {

    companion object{
        const val NEW_MENTOR = 1
        const val RECOMMEND_MENTOR = 2
        const val HOME = 3
        const val COMMUNITY = 4
        const val SETTING = 5
    }

    sealed class FragmentState {
        object newMentor : FragmentState()
        object recommendMentor : FragmentState()
        object home : FragmentState()
        object community : FragmentState()
        object setting : FragmentState()
    }

    private var _fragmentState = MutableLiveData<FragmentState>()
    val fragmentState: LiveData<FragmentState> get() = _fragmentState

    private var _writeMentorPost = MutableLiveData<MentorPost>()
    val writeMentorPost: LiveData<MentorPost> get() = _writeMentorPost

    private val _token = MutableLiveData<String>("")
    val token: LiveData<String> get() = _token

    fun updateFragmentState(fragmentState: FragmentState){
        _fragmentState.value = fragmentState
    }

    fun loadToken(): String {
        return _token.value!!
    }

    fun setToken(token: String) {
        _token.value = token
    }

    fun getNewMentorList() = viewModelScope.launch {
        mentorRepository.getNewMentorList().collect { list ->
            MentorStore.updateNewMentorList(list)
        }
    }

    fun getRecommendMentorList() = viewModelScope.launch {
        mentorRepository.getRecommendMentorList().collect { list ->
            MentorStore.updateRecommendMentorList(list)
        }
    }

    fun getCategoryMentorPostList(categoryId: Int) = viewModelScope.launch {
        mentorRepository.getCategoryMentorPostList(categoryId).collect { response ->
            Log.e("POST LIST", response.toString())


        }
    }

    fun setWriteMentorPost(mentorPost: MentorPost) {
        _writeMentorPost.value = mentorPost
    }


    fun sendWriteMentorPost(mentorPost: MentorPost) = viewModelScope.launch {
        mentorRepository.sendMentorPost(mentorPost).collect { response ->
            Log.e("sendWriteMentorPost", response.toString())
        }
    }
}