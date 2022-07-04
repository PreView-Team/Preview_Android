package preview.android.activity.api

import preview.android.activity.api.dto.LoginData
import preview.android.activity.api.dto.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/api/login")
    fun login(@Body token: LoginData): Call<LoginResponse>
}