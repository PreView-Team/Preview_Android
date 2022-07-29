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

    @POST("/api/post")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Body writing: Writing
    ): Response<MentorPostResponse>

    @GET("/api/post/category/")
    suspend fun getCatergoryPostList(
        @Header("Authorization") token: String,
        @Query("status") status: String,
        @Query("name") categoryName: String
    ): Response<JsonArray>

    @POST("/api/authority/{kakaoId}")
    suspend fun registMentor(
        @Header("Authorization") token: String,
        @Path("kakaoId") kakoId: Long
    ): Response<MentorRegistResponse>

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

    @GET("/api/post/{postId}")
    suspend fun getPostDetail(
        @Header("Authorization") token: String,
        @Path("postId") postId: Int
    ): Response<PostDetailResponse>

    @POST("/api/form")
    suspend fun createFrom(
        @Header("Authorization") token: String,
        @Body fom: Form
    ): Response<FormResponse>

    @GET("/api/form")
    suspend fun getAllMyForm(
        @Header("Authorization") token: String
    ): Response<JsonArray>

    @GET("/api/mentor/form")
    suspend fun getAllReceiveForm(
        @Header("Authorization") token: String
    ): Response<JsonArray>

    @GET("/api/form/{formId}")
    suspend fun getFormDetail(
        @Header("Authorization") token: String,
        @Path("formId") formId: Int
    ): Response<FormDetailResponse>

    @GET("/api/mentor/form/{formId}")
    suspend fun getReceiveFormDetail(
        @Header("Authorization") token: String,
        @Path("formId") formId: Int
    ): Response<ReceiveFormDetailResponse>
}