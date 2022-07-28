package preview.android.activity.util

import android.R.array
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import preview.android.R
import preview.android.model.MentorPost
import preview.android.model.Post


var deviceToken = ""

const val ERROR_CODE_400: String = "400"


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
            postId = 1,
            nickname = "1번",
            category = "마케터",
            introduce = "소개 1번",
            contents = "내용 1번 내용 1번 내용 1번 내용 1번\n 내용 1번 내용 1번 내용 1번 내용 1번\n내용 1번 내용 1번 내용 1번 내용 1번\n내용 1번 내용 1번 내용 1번 내용 1번",
            like = true,
            likeCount = 123,
        )
    )
    list.add(
        MentorPost(
            postId = 2,
            nickname = "2번",
            category = "마케터",
            introduce = "소개 2번",
            contents = "내용 1번 내용 1번 내용 1번 내용 1번\n 내용 1번 내용 1번 내용 1번 내용 1번\n내용 1번 내용 1번 내용 1번 내용 1번\n내용 1번 내용 1번 내용 1번 내용 1번",
            like = true,
            likeCount = 456,
        )
    )
    return list
}

fun getToken(): String {
    if (deviceToken == "") {
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

fun changeWordPointColor(view: TextView, word: String): SpannableString {
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

fun changeWordSkyBlueColor(view: TextView, word: String): SpannableString {
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

fun progressOn(progressDialog: AppCompatDialog) {
    progressDialog.setCancelable(false)
    progressDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    progressDialog.setContentView(R.layout.loading_dialog_custom)
    progressDialog.show()
}

fun progressOff(progressDialog: AppCompatDialog) {
    if (progressDialog != null && progressDialog.isShowing()) {
        progressDialog.dismiss()
    }
}

fun isFabOpened(view: FloatingActionButton): Boolean {
    return view.backgroundTintList!!.equals(
        AppCompatResources.getColorStateList(view.context, R.color.navy)
    )
}

fun changeFabClose(view: FloatingActionButton, dialog: ConstraintLayout) {
    ObjectAnimator.ofFloat(dialog, "translationY", 0f).apply { start() }
    view.setImageResource(R.drawable.ic_baseline_add)
    view.backgroundTintList = AppCompatResources.getColorStateList(view.context, R.color.orange)
    dialog.visibility = View.INVISIBLE
}

fun changeFabOpen(view: FloatingActionButton, dialog: ConstraintLayout) {
    dialog.visibility = View.VISIBLE
    ObjectAnimator.ofFloat(dialog, "translationY", -20f).apply { start() }
    view.setImageResource(R.drawable.ic_baseline_close)
    view.backgroundTintList = AppCompatResources.getColorStateList(view.context, R.color.navy)
}

fun showDialogFragment(activity: AppCompatActivity, newFragment: DialogFragment) {

    val fragmentManager = activity.supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    transaction
        .add(android.R.id.content, newFragment)
        .addToBackStack(null)
        .commit()
}

fun filtPostArray(list: JsonArray): List<MentorPost> {
    val type = object : TypeToken<List<MentorPost>>() {}.type

    return Gson().fromJson(list, type)

}