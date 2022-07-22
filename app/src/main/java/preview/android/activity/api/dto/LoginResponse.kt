package preview.android.activity.api.dto

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName(value = "kakaoAccessToken") var kakaoAccessToken: String
)

data class LoginResponse(
    @SerializedName(value = "token") var token: String?,
    @SerializedName(value = "code") var code: String?,
    @SerializedName(value = "message") var message: String?,
    @SerializedName(value = "httpCode") var httpCode: String?
)
