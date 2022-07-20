package preview.android.activity.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import preview.android.R
import preview.android.model.MentorPost
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

fun createMentorList(): List<MentorPost> {
    val list = arrayListOf<MentorPost>()
    list.add(
        MentorPost(
            nickname = "프리뷰1",
            tag = "#대기업",
            title = "first",
            content = "content1\ncontent1",
            comments = 20,
            likes = 20
        )
    )
    list.add(
        MentorPost(
            nickname = "프리뷰2",
            tag = "#대기업",
            title = "second",
            content = "content2\ncontent2",
            comments = 20,
            likes = 20
        )
    )
    list.add(
        MentorPost(
            nickname = "프리뷰3",
            tag = "#대기업",
            title = "third",
            content = "content3\ncontent2",
            comments = 20,
            likes = 20
        )
    )
    list.add(
        MentorPost(
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

fun changeWordPointColor(view : TextView, word : String): SpannableString {
    val description = view.text
    val spannableString = SpannableString(description)
    val start = description.indexOf(word)
    val end = start + word.length

    spannableString.setSpan(
        ForegroundColorSpan(Color.parseColor("#FDB022")),
        start,
        end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannableString
}

fun changeWordSkyBlueColor(view : TextView, word : String): SpannableString {
    val description = view.text
    val spannableString = SpannableString(description)
    val start = description.indexOf(word)
    val end = start + word.length

    spannableString.setSpan(
        ForegroundColorSpan(Color.parseColor("#2E90FA")),
        start,
        end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannableString
}

fun progressOn(progressDialog : AppCompatDialog){
    progressDialog.setCancelable(false)
    progressDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    progressDialog.setContentView(R.layout.loading_dialog_custom)
    progressDialog.show()
}
fun progressOff(progressDialog : AppCompatDialog){
    if(progressDialog != null && progressDialog.isShowing()){
        progressDialog.dismiss()
    }
}