package preview.android.activity.util

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import preview.android.R
import preview.android.data.AccountStore
import preview.android.data.AccountStore.updateFcmToken
import preview.android.model.MentorPost
import preview.android.model.Post
import java.io.File
import java.io.FileInputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


var deviceToken = ""

const val ERROR_CODE_400: String = "400"
const val ERROR_UNAUTHORIZED: String = "401"

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

fun getFCMToken(): String {
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
        Log.e("fcmtoken!!: ", deviceToken.toString())
    }
    return deviceToken
}

fun changeWordColor(view: TextView, word: String, color: String): SpannableString {
    val description = view.text
    val spannableString = SpannableString(description)
    val start = description.indexOf(word)
    val end = start + word.length

    when (color) {
        "point" -> {
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#FDB022")),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        "skyblue" -> {
            spannableString.setSpan(
                ForegroundColorSpan(Color.parseColor("#2E90FA")),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

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

fun changeFabClose(view: FloatingActionButton, dialog: ConstraintLayout, resource : Int) {
    ObjectAnimator.ofFloat(dialog, "translationY", 0f).apply { start() }
    view.setImageResource(resource)
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

inline fun <reified T> filtJsonArray(list: JsonArray): List<T> {
    val type = object : TypeToken<List<T>>() {}.type
    return Gson().fromJson(list, type)
}

fun getCurrentTime() : String{
    val curTime = Date()
    val format = SimpleDateFormat("MM/dd a h:mm분", Locale.KOREAN)
    val timeZone = TimeZone.getTimeZone("Asia/Seoul")
    format.timeZone = timeZone

    return format.format(curTime)
}
inline fun <reified T> DocumentSnapshot.toObjectNonNull(): T = toObject(T::class.java)!!

fun checkCertifyProgressOn(progressDialog: AppCompatDialog) {
    progressDialog.setCancelable(false)
    progressDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    progressDialog.setContentView(R.layout.check_certify_dialog)
    progressDialog.show()
}

fun checkCertifyProgressOff(progressDialog: AppCompatDialog) {
    if (progressDialog != null && progressDialog.isShowing()) {
        progressDialog.dismiss()
    }
}

fun checkCalendarProgressOn(context : Context, btnOkClicked : () -> Unit) {
   val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.check_calendar_dialog)
    dialog.show()
    dialog.findViewById<MaterialButton>(R.id.btn_ok).setOnClickListener {
        btnOkClicked()
        dialog.dismiss()
    }
    dialog.findViewById<MaterialButton>(R.id.btn_cancel_calendar).setOnClickListener {
        dialog.dismiss()
    }
}


fun checkLogoutProgressOn(context : Context, btnOkClicked : () -> Unit) {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.check_logout_dialog)
    dialog.show()
    dialog.findViewById<MaterialButton>(R.id.btn_ok).setOnClickListener {
        btnOkClicked()
        dialog.dismiss()
    }
    dialog.findViewById<MaterialButton>(R.id.btn_cancel_calendar).setOnClickListener {
        dialog.dismiss()
    }
}

fun checkSignoutProgressOn(context : Context, btnOkClicked : () -> Unit) {
    val dialog = Dialog(context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.check_signout_dialog)
    dialog.show()
    dialog.findViewById<MaterialButton>(R.id.btn_ok).setOnClickListener {
        btnOkClicked()
        dialog.dismiss()
    }
    dialog.findViewById<MaterialButton>(R.id.btn_cancel_calendar).setOnClickListener {
        dialog.dismiss()
    }
}

fun getJobList() : List<String>{
    return listOf("개발","경영ㆍ비즈니스","마케팅","디자인")
}
