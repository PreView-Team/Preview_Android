package preview.android.repository

import android.util.Log
import kotlinx.coroutines.flow.callbackFlow
import preview.android.activity.api.MentorService
import preview.android.activity.util.createMentorList
import preview.android.model.*

class MentorRepository(private val api: MentorService) {

    suspend fun getNewMentorThumbnailList() = callbackFlow {
        trySend(createMentorList())
        close()
    }

    suspend fun getRecommendMentorThumbnailList() = callbackFlow {
        trySend(createMentorList())
        close()
    }

    suspend fun getCategoryNewMentorPostList(token: String, categoryName: String) = callbackFlow {
        val request = api.getCatergoryPostList("Bearer $token", "new", categoryName)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend("ERROR :" + request.errorBody()!!.string())
        }
        close()
    }


    suspend fun getCategoryRecommendMentorPostList(token: String, categoryName: String) = callbackFlow {
        val request = api.getCatergoryPostList("Bearer $token", "recommendation",categoryName)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend("ERROR :" + request.errorBody()!!.string())
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

    suspend fun getSendForms(token: String) = callbackFlow {
        val request = api.getAllMyForm("Bearer $token")
        if(request.isSuccessful && request.body() != null){
            trySend(request.body()!!)
        }
        else{
            Log.e("getSendForms ERROR", request.code().toString())
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun getReceiveForms(token: String) = callbackFlow {
        val request = api.getAllReceiveForm("Bearer $token")
        if(request.isSuccessful && request.body() != null){
            trySend(request.body()!!)
        }
        else{
            Log.e("getReceiveForms ERROR", request.code().toString())
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun getFormDetail(token: String, formId: Int) = callbackFlow {
        val request = api.getFormDetail("Bearer $token", formId)
        if(request.isSuccessful && request.body() != null){
            trySend(request.body()!!)
        }
        else{
            Log.e("getFormDetail ERROR", request.code().toString())
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun getReceiveFormDetail(token: String, formId: Int) = callbackFlow {
        val request = api.getReceiveFormDetail("Bearer $token", formId)
        if(request.isSuccessful && request.body() != null){
            trySend(request.body()!!)
        }
        else{
            Log.e("getFormDetail ERROR", request.code().toString())
            trySend(request.errorBody()!!.string())
        }
        close()
    }
}