package preview.android.activity.api.dto

data class PostDetailResponse(
    val contents : String,
    val id : Int,
    val title :String,
    val subtitle : String,
    val nickname : String,
        val categoryName : String,
    val createDateTime : String,
    val updateDateTime : String,
    val checkedLike : Boolean
)
