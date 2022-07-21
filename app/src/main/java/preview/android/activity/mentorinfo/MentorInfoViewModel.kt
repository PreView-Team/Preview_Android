package preview.android.activity.mentorinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import preview.android.BaseViewModel
import preview.android.model.Form

class MentorInfoViewModel : BaseViewModel() {
    private val _response = MutableLiveData<String>()
    val response : LiveData<String> get() = _response

    fun sendForm(form : Form){
        _response.value = "test"
    }
}