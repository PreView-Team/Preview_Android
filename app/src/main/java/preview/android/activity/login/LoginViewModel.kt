package preview.android.activity.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.model.Account
import preview.android.repository.LoginRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel() {
    private val _refreshToken = MutableLiveData<String>("")
    val refreshToken: LiveData<String> get() = _refreshToken

    private val _account = MutableLiveData<Account>()
    val account: LiveData<Account> get() = _account

    private val _responseResult = MutableLiveData<String>()
    val responseResult: LiveData<String> get() = _responseResult

    fun loadRefreshToken(): String {
        return _refreshToken.value!!
    }

    fun setRefreshToken(token: String) {
        _refreshToken.value = token
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

    fun loginKaKao(context: Context) = viewModelScope.launch {
        runCatching {
            loginRepository.login(context)
        }.onSuccess { value ->
            setAccount(value)
            setRefreshToken(value.refreshToken)
        }.onFailure {
            throw Throwable(it)
        }
    }

    fun signUp(account: Account) = viewModelScope.launch {

        loginRepository.signUp(account).collect { value ->
            Log.e("RESULT", value)
        }
    }

    fun loginToServer(account: Account) = viewModelScope.launch {

        loginRepository.loginToServer(account).collect { value ->
            Log.e("LOGINTOSERVER", value)
        }

    }
}