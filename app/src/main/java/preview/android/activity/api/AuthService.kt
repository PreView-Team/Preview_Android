package preview.android.activity.api

import preview.android.activity.api.dto.LoginData
import preview.android.activity.api.dto.LoginResponse
import preview.android.activity.api.dto.SignUpResponse
import preview.android.model.Account
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/user/kakao/signup")
    suspend fun signUp(@Body account: Account): Response<SignUpResponse>

    @POST("/api/login")
    suspend fun login(@Body token: LoginData): Response<LoginResponse>
}