package preview.android.activity.api.dto

import preview.android.model.Review

data class PostDetailResponse(
    val postId: Int,
    val title: String,
    val contents: String,
    val nickname: String,
    val jobList: List<String>,
    val createDateTime: String,
    val updateDateTime: String,
    val introduce: String,
    val reviewCnt: Int,
    val reviews: List<Review>,
    val grade : Float,
)
