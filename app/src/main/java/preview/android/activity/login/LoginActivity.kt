package preview.android.activity.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.api.AuthService
import preview.android.activity.api.dto.LoginResponse
import preview.android.activity.api.LoginView
import preview.android.activity.api.dto.LoginData
import preview.android.activity.main.MainActivity
import preview.android.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(
    R.layout.activity_login
), LoginView {
    override val vm: LoginViewModel by viewModels()
    private lateinit var infoInputFragment: InfoInputFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var keyHash = Utility.getKeyHash(this)
        Log.e(TAG, "해시 키 값 : ${keyHash}")

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오계정 로그인 성공/accessToken ${token.accessToken}")
                Log.i(TAG, "카카오계정 로그인 성공/refreshToken ${token.refreshToken}")

                //닉네임, 이메일 받기
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error)
                    } else if (user != null) {
                        Log.i(
                            TAG, "사용자 정보 요청 성공" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n이메일: ${user.kakaoAccount?.email}"
                        )
                    }
                }

                //LoginViewModel에 refreshToken 저장
                vm.setRefreshToken(token.refreshToken)

                //서버에 POST
                login(LoginData(token.refreshToken))
            }
        }

        binding.btnKakao.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오톡으로 로그인 실패", error)

                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    } else if (token != null) {
                        Log.i(TAG, "카카오톡 로그인 성공/accessToken ${token.accessToken}")
                        Log.i(TAG, "카카오톡 로그인 성공/refreshToken ${token.refreshToken}")

                        //닉네임, 이메일 받기
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                Log.e(TAG, "사용자 정보 요청 실패", error)
                            } else if (user != null) {
                                Log.i(
                                    TAG, "사용자 정보 요청 성공" +
                                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                            "\n이메일: ${user.kakaoAccount?.email}"
                                )
                            }
                        }

                        //LoginViewModel에 refreshToken 저장
                        vm.setRefreshToken(token.refreshToken)

                        //서버에 POST
                        login(LoginData(token.refreshToken))
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }

    private fun login(token: LoginData) {
        val authService = AuthService()
        authService.setLoginView(this)

        authService.login(token)
    }

    override fun onLoginSuccess(result: LoginResponse) {
        //true면 로그인 진행 -> 바로 MainActivity로 이동
        startActivity(Intent(this, MainActivity::class.java))
        //false면 회원가입 진행 -> CompleteSignUpFragment로 이동
        supportFragmentManager.beginTransaction().replace(binding.layoutLogin.id, infoInputFragment)
            .commit()
    }

    override fun onLoginFailure() {
        Log.d("LOGIN/FAILURE", "로그인을 실패했습니다.")
    }
}