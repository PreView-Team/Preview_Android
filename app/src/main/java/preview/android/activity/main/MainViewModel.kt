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

    private var _writeMentorPost = MutableLiveData<MentorPost>()
    val writeMentorPost: LiveData<MentorPost> get() = _writeMentorPost
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