package preview.android.repository

import android.util.Log
import kotlinx.coroutines.flow.callbackFlow
import preview.android.activity.api.MentorService
import preview.android.activity.util.createMentorList
import preview.android.model.Form
import preview.android.model.MentorPost
import preview.android.model.PostId
import preview.android.model.Writing

class MentorRepository(private val api: MentorService) {

    suspend fun getNewMentorThumbnailList() = callbackFlow {
        trySend(createMentorList())
        close()
    }

    suspend fun getRecommendMentorThumbnailList() = callbackFlow {
        trySend(createMentorList())
        close()
    }

    suspend fun getCategoryMentorPostList(token: String, categoryId: Int) = callbackFlow {
        val request = api.getCatergoryPostList("Bearer $token", categoryId)
        if (request.isSuccessful && request.body() != null) {
            trySend(createMentorList())

        } else {
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun sendWriting(token: String, writing: Writing) = callbackFlow {
        val request = api.createPost("Bearer $token", writing)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun registMentor(token: String, kakaoId: Long) = callbackFlow {
        val request = api.registMentor("Bearer $token", kakaoId)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            Log.e("REGIST ERROR", request.code().toString())
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun likePost(token: String, postId: Int) = callbackFlow {
        val request = api.like("Bearer $token", PostId(postId = postId))
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            Log.e("likePost ERROR", request.code().toString())
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun unLikePost(token: String, postId: Int) = callbackFlow {
        val request = api.unlike("Bearer $token", PostId(postId = postId))
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            Log.e("unLikePost ERROR", request.code().toString())
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun getPostDetail(token: String, postId: Int) = callbackFlow {
        val request = api.getPostDetail("Bearer $token", postId)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            Log.e("getPostDetail ERROR", request.code().toString())
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun sendForm(token: String, form: Form) = callbackFlow {
        val request = api.createFrom("Bearer $token", form)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            Log.e("sendForm ERROR", request.code().toString())
            trySend(request.errorBody()!!.string())
        }
        close()
    }
}