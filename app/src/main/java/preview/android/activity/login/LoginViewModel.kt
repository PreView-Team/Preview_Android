package preview.android.activity.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.activity.api.dto.*
import preview.android.data.AccountStore
import preview.android.model.Account
import preview.android.model.AlarmObject
import preview.android.model.EditMentorInfo
import preview.android.repository.AlarmRepository
import preview.android.repository.ChatRepository
import preview.android.repository.LoginRepository
import preview.android.repository.MentorRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val alarmRepository: AlarmRepository,
    private val mentorRepository: MentorRepository,
    private val chatRepository: ChatRepository
) : BaseViewModel() {
    private val _refreshToken = MutableLiveData<String>("")
    val refreshToken: LiveData<String> get() = _refreshToken

    private val _kakaoAccessToken = MutableLiveData<String>("")
    val kakaoAccessToken: LiveData<String> get() = _kakaoAccessToken

    private val _kakaoAccount = MutableLiveData<Account>()
    val kakaoAccount: LiveData<Account> get() = _kakaoAccount

    private val _responseResult = MutableLiveData<String>()
    val responseResult: LiveData<String> get() = _responseResult

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> get() = _nickname

    private val _signUpResponseResult = MutableLiveData<String>()
    val signUpResponseResult: LiveData<String> get() = _signUpResponseResult

    private val _nicknameResponseResult = MutableLiveData<String>()
    val nicknameResponseResult: LiveData<String> get() = _nicknameResponseResult

    private val _mentorNicknameEditResult = MutableLiveData<String>()
    val mentorNicknameEditResult: LiveData<String> get() = _mentorNicknameEditResult

    private val _editUserResponseResult = MutableLiveData<String>()
    val editUserResponseResult: LiveData<String> get() = _editUserResponseResult

    private val _signOutResponseResult = MutableLiveData<Int>()
    val signOutResponseResult: LiveData<Int> get() = _signOutResponseResult

    private val _getUserInfoResponseResult = MutableLiveData<GetUserInfoResponse>()
    val getUserInfoResponseResult : LiveData<GetUserInfoResponse> get() = _getUserInfoResponseResult

    private val _getMentorInfoResponseResult = MutableLiveData<GetMentorInfoResponse>()
    val getMentorInfoResponseResult : LiveData<GetMentorInfoResponse> get() = _getMentorInfoResponseResult

    fun loadRefreshToken(): String {
        return _refreshToken.value!!
    }

    fun setRefreshToken(token: String) {
        _refreshToken.value = token
    }

    fun loadKakaoAccessToken(): String {
        return _kakaoAccessToken.value!!
    }

    fun setKakaoAccessToken(token: String) {
        _kakaoAccessToken.value = token
    }

    fun loadAccount(): Account {
        return _kakaoAccount.value!!
    }

    fun setKakaoAccount(account: Account) {
        _kakaoAccount.value = account
    }

    fun loadResponseResult(): String {
        return _responseResult.value!!
    }

    fun setResponseResult(result: String) {
        _responseResult.value = result
    }

    fun loadNickname(): String {
        return _nickname.value!!
    }

    fun setNickname(nickname: String) {
        _nickname.value = nickname
    }

    fun loadNicknameResponseResult(): String {
        return _nicknameResponseResult.value!!
    }

    fun setNicknameResponseResult(result: String) {
        _nicknameResponseResult.value = result
    }

    fun setEditUserResponseResult(result: String) {
        _editUserResponseResult.value = result
    }

    fun setSignOutResponseResult(result: Int) {
        _signOutResponseResult.value = result
    }

    fun setSignUpResponseResult(result: String) {
        _signUpResponseResult.value = result
    }

    fun setGetUserInfoResponseResult(result : GetUserInfoResponse){
        _getUserInfoResponseResult.value = result
    }

    fun setGetMentorInfoResponseResult(result : GetMentorInfoResponse){
        _getMentorInfoResponseResult.value = result
    }

    fun setMentorNicknameEditResult(result : String){
        _mentorNicknameEditResult.value = result
    }

    fun loginKaKao(context: Context) = viewModelScope.launch {
        runCatching {
            loginRepository.login(context)
        }.onSuccess { value ->
            when (value) {
                is Account -> {
                    Log.e("LOGINKAKO accoutn", value.toString())
                    setKakaoAccount(value)
                    setKakaoAccessToken(value.kakaoAccessToken)
                }
                is Throwable -> {
                    // 에러 원인별로 토스트메시지 표시
                }
                else -> {

                }
            }
        }.onFailure {
            throw Throwable(it)
        }
    }

    fun signUp(account: Account) = viewModelScope.launch {

        loginRepository.signUp(account).collect { value ->
            Log.e("SIGNUP", value.toString())
            when (value) {
                is String -> {
                    setSignUpResponseResult(value)
                }
                else -> {
                    setSignUpResponseResult("success")
                }
            }
        }
    }

    fun loginToServer(account: Account) = viewModelScope.launch {
        loginRepository.loginToServer(account).collect { value ->
            if (value is LoginResponse) {
                Log.e("LOGINTOSERVER response", value.toString())
                setResponseResult(value.token!!)
            } else {
                Log.e("else response", value.toString())
                setResponseResult(value.toString())
            }
        }

    }

    fun checkNickname(nickname: String) = viewModelScope.launch {

        loginRepository.checkNickname(nickname).collect { value ->
            Log.e("NICKNAME CHECK", value)
            setNicknameResponseResult(value)
        }
    }


    fun editUser(token: String, editUser: EditUserData) = viewModelScope.launch {

        loginRepository.editUser(token, editUser).collect { value ->
            Log.e("USER EDIT", value)
            setEditUserResponseResult(value)
        }
    }

    fun signOut(token: String) = viewModelScope.launch {

        loginRepository.signOut(token).collect { value ->
            Log.e("SIGNOUT", value.toString())
            setSignOutResponseResult(value)
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

    fun getUserDetail(token: String) = viewModelScope.launch {
        loginRepository.getUserInfo(token).collect{
            if(it is GetUserInfoResponse) {
                Log.e("getUserDetail", it.toString())
                setGetUserInfoResponseResult(it)
            }
            else{
                setGetMentorInfoResponseResult(GetMentorInfoResponse())
            }
        }
    }

    fun getMentorInfo(token: String)= viewModelScope.launch {
        mentorRepository.getMentorInfo(token).collect {
            val getMentorInfoResponse = it as GetMentorInfoResponse
            Log.e("getMentorInfo", it.toString())
            setGetMentorInfoResponseResult(getMentorInfoResponse)
        }
    }
    fun editMentorInfo(token:String, editMentorInfo: EditMentorInfo) = viewModelScope.launch {
        mentorRepository.editMentorInfo(token, editMentorInfo).collect { value ->
            setMentorNicknameEditResult(value.toString())
        }
    }

    fun editChatRoom(originName : String, changeName : String) = viewModelScope.launch {
        chatRepository.editChatRoom(originName, changeName).collect { value ->
            Log.e("EDITCHAGE ROOM", value.toString())
        }
    }
}