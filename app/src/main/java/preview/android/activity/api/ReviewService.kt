package preview.android.activity.api

import preview.android.activity.api.dto.FormResponse
import preview.android.model.Review
import retrofit2.Response
import retrofit2.http.*

interface ReviewService {

    @POST("api/post/{postId}/review")
    suspend fun createReview(
        @Header("Authorization") token: String,
        @Path("postId") postId: Int,
        @Body review: Review
    ): Response<FormResponse>

    @PUT("/api/review/{reviewId}")
    suspend fun editReview(
        @Header("Authorization") token: String,
        @Path("reviewId") reviewId: Int,
        @Body review: Review
    ): Response<FormResponse>

    @DELETE("/api/reivew/{reviewId}")
    suspend fun deleteReview(
        @Header("Authorization") token: String,
        @Path("reviewId") reviewId: Int
    ): Response<FormResponse>

    @GET("/api/post/{postId}/review")
    suspend fun getReviewList(
        @Header("Authorization") token: String,
        @Path("postId") postId: Int,
        @Query("page") page : Int,
        @Query("size") size : Int
    ): Response<FormResponse>
}