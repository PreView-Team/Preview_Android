package preview.android.activity.api.dto

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName(value="token") var token : String
)

data class LoginResponse(
    @SerializedName(value = "token") var token: String
)
