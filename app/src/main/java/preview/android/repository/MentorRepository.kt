package preview.android.repository

import kotlinx.coroutines.flow.callbackFlow
import preview.android.activity.api.MentorService
import preview.android.activity.util.createMentorList
import preview.android.model.MentorPost

class MentorRepository(private val api: MentorService) {

    suspend fun getNewMentorList() = callbackFlow {
        trySend(createMentorList())
        close()
    }

    suspend fun getRecommendMentorList() = callbackFlow {
        trySend(createMentorList())
        close()
    }

    suspend fun getFirstMentorPostList() = callbackFlow {
        trySend(createMentorList())
        close()
    }

    suspend fun sendMentorPost(mentorPost: MentorPost) = callbackFlow {
        val request = api.createPost(mentorPost)
        if (request.isSuccessful && request.body() != null) {
            trySend(request.body()!!)
        } else {
            trySend(request.errorBody()!!.string())
        }
        close()
    }
}