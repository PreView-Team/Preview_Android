package preview.android.repository

import android.util.Log
import kotlinx.coroutines.flow.callbackFlow
import preview.android.activity.api.MentorService
import preview.android.model.*

class MentorRepository(private val api: MentorService) {

    suspend fun getNewMentorThumbnailList(token: String, page: Int, size: Int, sort: String) =
        callbackFlow {
            val request = api.getHomeMentorThumbnail("Bearer $token", "new", page, size, sort)
            if (request.isSuccessful && request.body() != null) {
                trySend(request.body()!!)
            } else {
                trySend("ERROR :" + request.errorBody()!!.string())
            }
            close()
        }

    suspend fun getRecommendMentorThumbnailList(token: String, page: Int, size: Int, sort: String) =
        callbackFlow {
            val request =
                api.getHomeMentorThumbnail("Bearer $token", "recommendation", page, size, sort)
            if (request.isSuccessful && request.body() != null) {
                trySend(request.body()!!)
            } else {
                trySend("ERROR :" + request.errorBody()!!.string())
            }
            close()
        }

    suspend fun getCategoryNewMentorPostList(
        token: String,
        categoryName: String,
        page: Int,
        size: Int,
        sort: String
    ) = callbackFlow {
        val request =
            api.getCatergoryPostList("Bearer $token", "new", categoryName, page, size, sort)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend("ERROR :" + request.errorBody()!!.string())
        }
        close()
    }


    suspend fun getCategoryRecommendMentorPostList(
        token: String,
        categoryName: String,
        page: Int,
        size: Int,
        sort: String
    ) =
        callbackFlow {
            val request = api.getCatergoryPostList("Bearer $token", "recommendation", categoryName, page, size, sort)
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
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun likePost(token: String, postId: Int) = callbackFlow {
        val request = api.like("Bearer $token", PostId(postId = postId))
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun unLikePost(token: String, postId: Int) = callbackFlow {
        val request = api.unlike("Bearer $token", PostId(postId = postId))
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun getPostDetail(token: String, postId: Int, page: Int, size: Int, sort : String) = callbackFlow {
        val request = api.getPostDetail("Bearer $token", postId, page, size, sort)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend(request.errorBody()!!.string())
        }
        close()
    }
    suspend fun getPostDetail(token: String, postId: Int) = callbackFlow {
        val request = api.getPostDetail("Bearer $token", postId)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend(request.errorBody()!!.string())
        }
        close()
    }
    suspend fun editPost(token: String, editPost: EditPost) = callbackFlow {
        val request = api.editPost("Bearer $token", editPost)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun deletePost(token: String, postId: Int) = callbackFlow {
        val request = api.deletePost("Bearer $token", postId)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun getMentorInfo(token: String) = callbackFlow {
        val request = api.getMentorInfo("Bearer $token")
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun editMentorInfo(token: String, editMentorInfo: EditMentorInfo) = callbackFlow {
        val request = api.editMentorInfo("Bearer $token", editMentorInfo)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun getWritePosts(token: String) = callbackFlow {
        val request = api.getWritePosts("Bearer $token")
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun getWritePostDetail(token: String, postId: Int) = callbackFlow {
        val request = api.getWritePostDetail("Bearer $token", postId)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun searchMentors(
        token: String,
        keyword: String,
        category: String,
        page: Int,
        size: Int
    ) = callbackFlow {
        val request = api.getSearchMentors("Bearer $token", keyword, category, page, size, "id")
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend(request.errorBody()!!.string())
        }
        close()
    }

    suspend fun checkOverlapMentorNickname(token: String, mentorNickname: String) = callbackFlow {
        val request = api.checkOverlapMentorNickname("Bearer $token", mentorNickname)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend(request.errorBody()!!.string())
        }
        close()
    }
}