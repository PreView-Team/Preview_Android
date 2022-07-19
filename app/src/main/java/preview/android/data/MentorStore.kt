package preview.android.data

import androidx.lifecycle.LiveData
import preview.android.activity.util.MutableListLiveData
import preview.android.model.Mentor

object MentorStore {

    private val _newMentorList = MutableListLiveData<Mentor>()
    val newMentorList : LiveData<List<Mentor>> get() = _newMentorList

    private val _recommendMentorList = MutableListLiveData<Mentor>()
    val recommendMentorList : LiveData<List<Mentor>> get() = _recommendMentorList


    fun updateNewMentorList(list : List<Mentor>){
        _newMentorList.addAll(list)
    }

    fun updateRecommendMentorList(list : List<Mentor>){
        _recommendMentorList.addAll(list)
    }

}