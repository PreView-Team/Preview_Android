package preview.android.activity.api

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import org.json.JSONObject
import preview.android.activity.api.dto.MentorPostResponse
import preview.android.activity.api.dto.MentorRegistResponse
import preview.android.model.MentorPost
import retrofit2.Response
import retrofit2.http.*

interface MentorService {

    @POST("/api/post")
    suspend fun createPost(@Body mentorPost: MentorPost): Response<MentorPostResponse>

    @GET("/api/post/category/{categoryId}")
    suspend fun getCatergoryPostList(@Path("categoryId") categoryId: Int): Response<JsonObject>

    @POST("/api/authority/{kakaoId}")
    suspend fun registMentor(
        @Header("Authorization") token: String,
        @Path("kakaoId") kakoId: Long
    ): Response<MentorRegistResponse>
}