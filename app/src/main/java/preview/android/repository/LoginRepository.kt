package preview.android.repository

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import preview.android.activity.api.AuthService
import preview.android.model.Account
import kotlin.coroutines.resume

class LoginRepository(private val api: AuthService) {
    suspend fun login(context: Context): Account {
        var account = Account()
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            loginWithKakoTalk(context).let { oAuthToken ->
                account = account.copy(
                    accessToken = oAuthToken.accessToken,
                    refreshToken = oAuthToken.refreshToken
                )
            }
        } else {
            loginWithWebView(context).let { oAuthToken ->
                account = account.copy(
                    accessToken = oAuthToken.accessToken,
                    refreshToken = oAuthToken.refreshToken
                )
            }
        }
        getUser().let { user ->
            account = account.copy(nickname = user.kakaoAccount?.profile?.nickname!!)
        }
        return account
    }


    suspend fun loginWithKakoTalk(context: Context): OAuthToken =
        suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                continuation.resume(
                    when {
                        error != null -> throw error
                        token != null -> token
                        else -> throw Throwable("RESPONSE_NOTHING")
                    }
                )
            }
        }


    suspend fun loginWithWebView(context: Context): OAuthToken =
        suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                continuation.resume(
                    when {
                        error != null -> throw error
                        token != null -> token
                        else -> throw Throwable("RESPONSE_NOTHING")
                    }
                )
            }
        }

    suspend fun getUser(): User =
        suspendCancellableCoroutine { continuation ->
            UserApiClient.instance.me { user, error ->
                continuation.resume(
                    when {
                        error != null -> throw error
                        user != null -> user
                        else -> throw Throwable("RESPONSE_NOTHING")
                    }
                )
            }
        }

    suspend fun signUp(account: Account) = callbackFlow {

        val request = api.signUp(account)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!.result)
        } else {
            trySend(request.errorBody()!!.string())
        }

        close()
    }

    suspend fun loginToServer(account: Account) = callbackFlow {
        val request = api.login(account.refreshToken)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!.token)
        } else {
            trySend(request.errorBody()!!.string())
        }

        close()
    }
}