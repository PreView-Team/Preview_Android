package preview.android.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import preview.android.activity.util.MutableListLiveData
import preview.android.model.MentorPost

object AccountStore {

    val kakaoId = 2327958242

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> get() = _nickname


    private val _token = MutableLiveData<String>()
    val token: LiveData<String> get() = _token

    fun updateToken(token : String){
        _token.value = token
    }

    fun updateNickname(nickname : String){
        _nickname.value = nickname
    }
}