package preview.android.repository

import kotlinx.coroutines.flow.callbackFlow
import preview.android.activity.api.MentorService
import preview.android.activity.util.createMentorList

class MentorRepository(private val api: MentorService) {

    suspend fun getNewMentorList() = callbackFlow {
        trySend(createMentorList())
        close()
    }

    suspend fun getRecommendMentorList() = callbackFlow {
        trySend(createMentorList())
        close()
    }
}