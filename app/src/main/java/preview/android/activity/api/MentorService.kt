package preview.android.activity.api

import retrofit2.http.Body
import retrofit2.http.POST

interface MentorService {

    @POST
    fun createPost(@Body mentor)

}