package preview.android.activity.api

import android.util.Log
import preview.android.activity.api.dto.LoginData
import preview.android.activity.api.dto.LoginResponse
import preview.android.activity.login.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var loginView: LoginView

    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }

    //로그인
    fun login(token: LoginData){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(token).enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("LOGIN/SUCCESS", response.toString())
                if (response.isSuccessful) {
                    Log.d("LOGIN/SUCCESS", response.body()!!.toString())
                    val resp: LoginResponse = response.body()!!
                    loginView.onLoginSuccess(resp)
                } else {
                    Log.d("LOGIN/FAILURE", response.errorBody()!!.string())
                    loginView.onLoginFailure()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("LOGIN/FAILURE",t.message.toString())
            }

        } )

    }

}