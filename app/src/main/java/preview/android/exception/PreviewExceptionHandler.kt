package preview.android.exception


import android.app.Activity
import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import preview.android.activity.error.ErrorActivity
import java.io.PrintWriter
import java.io.StringWriter

class PreviewExceptionHandler(
    application: Application
) : Thread.UncaughtExceptionHandler {

    private var lastActivity: Activity? = null
    private var activityCount = 0

    init {
        application.registerActivityLifecycleCallbacks(
            object : SimpleActivityLifecycleCallbacks() {

                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    if (isSkipActivity(activity)) {
                        return
                    }
                    lastActivity = activity

                }

                override fun onActivityStarted(activity: Activity) {
                    if (isSkipActivity(activity)) {
                        return
                    }
                    activityCount++
                    lastActivity = activity

                }

                override fun onActivityStopped(activity: Activity) {
                    if (isSkipActivity(activity)) {
                        return
                    }
                    activityCount--
                    if (activityCount < 0) {
                        lastActivity = null
                    }

                }
            })
    }

    private fun isSkipActivity(activity: Activity) = activity is ErrorActivity


    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        lastActivity?.run {

            Firebase.crashlytics.run {
                setUserId("user0358")
                log("LOG")
                recordException(throwable)
            }
            val stringWriter = StringWriter()
            throwable.printStackTrace(PrintWriter(stringWriter))
            startErrorActivity(this, stringWriter.toString())
        }
        Process.killProcess(Process.myPid())
        System.exit(-1)
    }

    private fun startErrorActivity(activity: Activity, errorText: String) = activity.run {

        val errorActivityIntent = Intent(this, ErrorActivity::class.java)
            .apply {
                Log.e("startErrorActivity", "!!" + intent.toString())
                putExtra(ErrorActivity.EXTRA_INTENT, intent)
                putExtra(ErrorActivity.EXTRA_ERROR_TEXT, errorText)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }

        startActivity(errorActivityIntent)
        finish()
//        Process.killProcess(Process.myPid())
//        System.exit(-1)
    }

}