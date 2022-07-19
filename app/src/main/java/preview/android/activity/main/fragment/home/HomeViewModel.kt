package preview.android.activity.main.fragment.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.data.MentorStore
import preview.android.repository.MentorRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val mentorRepository: MentorRepository) :
    BaseViewModel() {


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
}