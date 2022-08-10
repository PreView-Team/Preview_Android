package preview.android.repository

import kotlinx.coroutines.flow.callbackFlow
import preview.android.activity.api.ReviewService
import preview.android.model.Review

class ReviewRepository(private val api: ReviewService) {

    fun createReview(token: String, postId: Int, review: Review) = callbackFlow {
        val request = api.createReview("Bearer $token", postId, review)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend("ERROR :" + request.errorBody()!!.string())
        }
        close()
    }

    fun editReview(token: String, postId: Int, review: Review) = callbackFlow {
        val request = api.editReview(token, postId, review)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend("ERROR :" + request.errorBody()!!.string())
        }
        close()
    }

    fun deleteReview(token: String, reviewId: Int) = callbackFlow {
        val request = api.deleteReview(token, reviewId)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend("ERROR :" + request.errorBody()!!.string())
        }
        close()
    }

    fun getReviewList(token: String, postId: Int, page : Int) = callbackFlow {
        val request = api.getReviewList(token, postId, page, 10)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend("ERROR :" + request.errorBody()!!.string())
        }
        close()
    }
}