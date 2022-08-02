package preview.android.activity.api.dto

import com.google.gson.annotations.SerializedName

data class NicknameResponse(
    val isValidNickname: Boolean
)

data class EditNickname(
    @SerializedName(value = "nickname") var nickname: String
)

data class EditNicknameResponse (
    val result: String
    )