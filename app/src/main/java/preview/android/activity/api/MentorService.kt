package preview.android.activity.api

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import org.json.JSONObject
import preview.android.activity.api.dto.*
import preview.android.model.*
import retrofit2.Response
import retrofit2.http.*

interface MentorService {

    @POST("/api/mentor/{kakaoId}")
    suspend fun registMentor(
        @Header("Authorization") token: String,
        @Path("kakaoId") kakoId: Long
    ): Response<MentorRegistResponse>

    @POST("/api/post")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Body writing: Writing
    ): Response<SignUpResponse>

    @GET("/api/post/{postId}/review")
    suspend fun getPostDetail(
        @Header("Authorization") token: String,
        @Path("postId") postId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): Response<PostDetailResponse>

    @GET("/api/post/{postId}")
    suspend fun getPostDetail(
        @Header("Authorization") token: String,
        @Path("postId") postId: Int
    ): Response<PostDetailResponse>

    @PUT("/api/post")
    suspend fun editPost(
        @Header("Authorization") token: String,
        @Body editPost: EditPost
    ): Response<MentorPostResponse>

    @DELETE("/api/post/{postId}")
    suspend fun deletePost(
        @Header("Authorization") token: String,
        @Path("postId") postId: Int
    ): Response<MentorPostResponse>


    @GET("/api/post/category/")
    suspend fun getCatergoryPostList(
        @Header("Authorization") token: String,
        @Query("status") status: String,
        @Query("name") categoryName: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): Response<JsonArray>


    @GET("/api/home/post/")
    suspend fun getHomeMentorThumbnail(
        @Header("Authorization") token: String,
        @Query("status") status: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String
    ): Response<JsonArray>


    @GET("/api/mentor")
    suspend fun getMentorInfo(
        @Header("Authorization") token: String
    ): Response<GetMentorInfoResponse>

    @GET("/api/mentor/nickname/{nickname}")
    suspend fun checkOverlapMentorNickname(
        @Header("Authorization") token: String,
        @Path("nickname") nickname: String
    ): Response<NicknameResponse>


    @POST("/api/post/like")
    suspend fun like(
        @Header("Authorization") token: String,
        @Body postId: PostId
    ): Response<LikeResponse>

    @POST("/api/post/unlike")
    suspend fun unlike(
        @Header("Authorization") token: String,
        @Body postId: PostId
    ): Response<LikeResponse>


    @PUT("/api/mentor")
    suspend fun editMentorInfo(
        @Header("Authorization") token: String,
        @Body editMentorInfo: EditMentorInfo
    ): Response<MentorRegistResponse>

    @GET("/api/mentor/post")
    suspend fun getWritePosts(
        @Header("Authorization") token: String,
    ): Response<JsonArray>

    @GET("/api/mentor/post/{postId}")
    suspend fun getWritePostDetail(
        @Header("Authorization") token: String,
        @Path("postId") postId: Int
    ): Response<ReceiveWritePost>

    @GET("/api/post/search")
    suspend fun getSearchMentors(
        @Header("Authorization") token: String,
        @Query("keyword") keyword: String,
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String,
    ): Response<JsonArray>
}