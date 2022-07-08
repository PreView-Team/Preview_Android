package preview.android.activity.util

import android.content.Context
import android.provider.Settings
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import preview.android.model.Mentor
import preview.android.model.Post


var deviceToken = ""

fun createBestPostList(): List<Post> {
    val list = arrayListOf<Post>()
    list.add(
        Post(
            title = "first",
            createDate = "2022.06.26",
            content = "content1\ncontent1",
            writer = "1",
            likes = 20
        )
    )
    list.add(
        Post(
            title = "second",
            createDate = "2022.06.26",
            content = "content2\ncontent2",
            writer = "2",
            likes = 21
        )
    )
    list.add(
        Post(
            title = "third",
            createDate = "2022.06.26",
            content = "content3\ncontent3",
            writer = "3",
            likes = 22
        )
    )
    list.add(
        Post(
            title = "fourth",
            createDate = "2022.06.26",
            content = "content4\ncontnet4",
            writer = "4",
            likes = 23
        )
    )

    return list
}

fun createMentorList(): List<Mentor> {
    val list = arrayListOf<Mentor>()
    list.add(
        Mentor(
            nickname = "프리뷰1",
            tag = "#대기업",
            title = "first",
            content = "content1\ncontent1",
            comments = 20,
            likes = 20
        )
    )
    list.add(
        Mentor(
            nickname = "프리뷰2",
            tag = "#대기업",
            title = "second",
            content = "content2\ncontent2",
            comments = 20,
            likes = 20
        )
    )
    list.add(
        Mentor(
            nickname = "프리뷰3",
            tag = "#대기업",
            title = "third",
            content = "content3\ncontent2",
            comments = 20,
            likes = 20
        )
    )
    list.add(
        Mentor(
            nickname = "프리뷰4",
            tag = "#대기업",
            title = "fourth",
            content = "content4\ncontent2",
            comments = 20,
            likes = 20
        )
    )

    return list
}

fun getToken() : String{
    if(deviceToken == "") {
        var task =
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("token ", "not successful")
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                deviceToken = task.result
            })
        if (task.isSuccessful) {
            deviceToken = task.result
        }
        Log.e("token: ", deviceToken.toString())
    }
    return deviceToken
}