package preview.android.activity.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.activity.api.dto.EditNickname
import preview.android.activity.api.dto.EditUserData
import preview.android.activity.api.dto.LoginResponse
import preview.android.model.Account
import preview.android.repository.LoginRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel() {
    private val _refreshToken = MutableLiveData<String>("")
    val refreshToken: LiveData<String> get() = _refreshToken

    private val _kakaoAccessToken = MutableLiveData<String>("")
    val kakaoAccessToken: LiveData<String> get() = _kakaoAccessToken

    private val _account = MutableLiveData<Account>()
    val account: LiveData<Account> get() = _account

    private val _responseResult = MutableLiveData<String>()
    val responseResult: LiveData<String> get() = _responseResult

    private val _nickname = MutableLiveData<String>()
    val nickname: LiveData<String> get() = _nickname

    private val _nicknameResponseResult = MutableLiveData<String>()
    val nicknameResponseResult: LiveData<String> get() = _nicknameResponseResult

    private val _editNicknameResponseResult = MutableLiveData<Int>()
    val editNicknameResponseResult: LiveData<Int> get() = _editNicknameResponseResult

    private val _signOutResponseResult = MutableLiveData<Int>()
    val signOutResponseResult: LiveData<Int> get() = _signOutResponseResult


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
        return _account.value!!
    }

    fun setAccount(account: Account) {
        _account.value = account
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

    fun setEditNicknameResponseResult(result: Int) {
        _editNicknameResponseResult.value = result
    }

    fun setSignOutResponseResult(result: Int) {
        _signOutResponseResult.value = result
    }

    fun loginKaKao(context: Context) = viewModelScope.launch {
        runCatching {
            loginRepository.login(context)
        }.onSuccess { value ->
            when (value) {
                is Account -> {
                    setAccount(value)
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
        }
    }

    fun loginToServer(account: Account) = viewModelScope.launch {

        loginRepository.loginToServer(account).collect { value ->
            Log.e("LOGINTOSERVER", value.toString())
            setResponseResult(value)
        }

    }

    fun checkNickname(nickname: String) = viewModelScope.launch {

        loginRepository.checkNickname(nickname).collect { value ->
            Log.e("NICKNAME CHECK", value.toString())
            setNicknameResponseResult(value.toString())
        }
    }

    fun editNickname(token: String, nickname: EditNickname) = viewModelScope.launch {

        loginRepository.editNickname(token, nickname).collect { value ->
            Log.e("NICKNAME EDIT", value.toString())
            setEditNicknameResponseResult(value)
        }
    }

    fun editUser(token: String,job: EditUserData) = viewModelScope.launch {

        loginRepository.editUser(token, job).collect { value ->
            Log.e("USER EDIT", value)
        }
    }

    fun signOut(token: String) = viewModelScope.launch {

        loginRepository.signOut(token).collect { value ->
            Log.e("SIGNOUT", value.toString())
            setSignOutResponseResult(value)
        }
    }

}