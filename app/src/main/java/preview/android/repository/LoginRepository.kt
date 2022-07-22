package preview.android.repository

import android.content.Context
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import preview.android.activity.api.AuthService
import preview.android.activity.api.dto.LoginData
import preview.android.model.Account
import kotlin.coroutines.resume

class LoginRepository(private val api: AuthService) {
    suspend fun login(context: Context): Any {
        var account = Account()
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            loginWithKakoTalk(context).let { value ->
                when (value) {
                    is OAuthToken -> {
                        account = account.copy(
                            kakaoAccessToken = value.accessToken,
//                    refreshToken = oAuthToken.refreshToken
                        )
                    }
                    is Throwable -> {
                        return value
                    }
                    else -> {
                        return "로그인 인증 중 에러가 발생했습니다. 다시 시도해주세요."
                    }
                }
            }
        } else {
            loginWithWebView(context).let { value ->
                when (value) {
                    is OAuthToken -> {
                        account = account.copy(
                            kakaoAccessToken = value.accessToken,
//                    refreshToken = oAuthToken.refreshToken
                        )
                    }
                    is Throwable -> {
                        return value
                    }
                    else -> {
                        return "로그인 인증 중 에러가 발생했습니다. 다시 시도해주세요."
                    }
                }
            }
        }
        getUser().let { value ->
            when(value){
                is User ->{
                    account = account.copy(nickname = value.kakaoAccount?.profile?.nickname!!)
                }
                is Throwable ->{
                    return value
                }
                else ->{
                    return "유저정보를 받아오는 도중 에러가 발생했습니다. 다시 시도해주세요."
                }
            }
        }
        return account
    }


    suspend fun loginWithKakoTalk(context: Context): Any =
        suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                continuation.resume(
                    when {
                        error != null -> error
                        token != null -> token
                        else -> "else"
                    }
                )
            }
        }


    suspend fun loginWithWebView(context: Context): Any =
        suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                continuation.resume(
                    when {
                        error != null -> error
                        token != null -> token
                        else -> "else"
                    }
                )
            }
        }

    suspend fun getUser(): Any =
        suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.me { user, error ->
                continuation.resume(
                    when {
                        error != null -> error
                        user != null -> user
                        else -> "else"
                    }
                )
            }
        }

    suspend fun signUp(account: Account) = callbackFlow {

        val request = api.signUp(account)
        if (request.isSuccessful && request.body() != null) {

            Log.i("SIGN UP:", request.body()!!.result)
            trySend(request.body()!!)
        } else {
            trySend(request.errorBody()!!.string())
            Log.i("errorBody:", request.errorBody()!!.string())
        }

        close()
    }

    suspend fun loginToServer(account: Account) = callbackFlow {
        val request = api.login(LoginData(account.kakaoAccessToken))
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!.token!!)
        } else {
            trySend(request.code().toString())
        }
        close()
    }

    suspend fun checkNickname(username: String) = callbackFlow {

        val request = api.nickname(username)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!.isValidNickname.toString())
        } else {
            trySend(request.errorBody()!!.string())
        }

        close()
    }
}