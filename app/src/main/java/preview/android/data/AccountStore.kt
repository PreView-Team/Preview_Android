package preview.android.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import preview.android.activity.util.MutableListLiveData
import preview.android.model.MentorPost

object AccountStore {

    val kakaoId = 2373880354
    // 2327958242 -> 주이식
    // 2373880354 -> 테스트계정

    private val _myFCMToken = MutableLiveData<String>()
    val myFCMToken: LiveData<String> get() = _myFCMToken

    private val _menteeNickname = MutableLiveData<String>()
    val menteeNickname: LiveData<String> get() = _menteeNickname

    private val _mentorNickname = MutableLiveData<String>()
    val mentorNickname: LiveData<String> get() = _mentorNickname

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> get() = _token

    private val _isMentored = MutableLiveData<Boolean>()
    val isMentored: LiveData<Boolean> get() = _isMentored

    private val _menteeJob = MutableLiveData<String>()
    val menteeJob: LiveData<String> get() = _menteeJob

    private val _mentorJob = MutableLiveData<String>()
    val mentorJob: LiveData<String> get() = _mentorJob

    fun updateMentorJob(job: String) {
        _mentorJob.value = job
    }
    fun updateMenteeJob(job: String) {
        _menteeJob.value = job
    }
    fun updateToken(token: String) {
        _token.value = token
    }

    fun updateFcmToken(fcmToken: String) {
        _myFCMToken.value = fcmToken
    }

    fun updateMenteeNickname(nickname: String) {
        _menteeNickname.value = nickname
    }

    fun updateMentorNickname(nickname: String) {
        _mentorNickname.value = nickname
    }

    fun updateIsMentored(isMentored: Boolean) {
        _isMentored.value = isMentored
    }
}