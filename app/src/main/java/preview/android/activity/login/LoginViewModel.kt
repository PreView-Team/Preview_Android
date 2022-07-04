package preview.android.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import preview.android.BaseViewModel


class LoginViewModel : BaseViewModel() {
    private val _refreshToken = MutableLiveData<String>("")
    val refreshToken: LiveData<String> get() = _refreshToken

    fun loadRefreshToken(): String {
        return _refreshToken.value!!
    }

    fun setRefreshToken(token: String) {
        _refreshToken.value = token
    }
}