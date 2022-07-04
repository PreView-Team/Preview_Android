package preview.android.activity.api

import preview.android.activity.api.dto.LoginResponse

interface LoginView {
    fun onLoginSuccess(result: LoginResponse)
    fun onLoginFailure()
}