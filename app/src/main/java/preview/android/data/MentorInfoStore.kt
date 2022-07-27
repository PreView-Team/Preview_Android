package preview.android.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import preview.android.model.Form
import preview.android.model.MentorPost

object MentorInfoStore {

    private val _mentorPost = MutableLiveData<MentorPost>()
    val mentorPost: LiveData<MentorPost> get() = _mentorPost

    private val _form = MutableLiveData<Form>()
    val form: LiveData<Form> get() = _form


    fun updateMentorPost(mentorPost: MentorPost) {
        _mentorPost.value = mentorPost
    }

    fun updateForm(form: Form) {
        _form.value = form
    }

}