package preview.android.data

import androidx.lifecycle.LiveData
import preview.android.activity.util.MutableListLiveData
import preview.android.model.MentorPost

object MentorStore {

    private val _newMentorList = MutableListLiveData<MentorPost>()
    val newMentorList : LiveData<List<MentorPost>> get() = _newMentorList

    private val _recommendMentorList = MutableListLiveData<MentorPost>()
    val recommendMentorList : LiveData<List<MentorPost>> get() = _recommendMentorList


    fun updateNewMentorList(list : List<MentorPost>){
        _newMentorList.clear()
        _newMentorList.addAll(list)
    }

    fun updateRecommendMentorList(list : List<MentorPost>){
        _recommendMentorList.clear()
        _recommendMentorList.addAll(list)
    }

}