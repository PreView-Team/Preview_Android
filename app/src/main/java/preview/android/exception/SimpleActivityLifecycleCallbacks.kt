package preview.android.exception


import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

abstract class SimpleActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityPaused(activity: Activity) {

        Log.e("onActivityPaused", "!!")
    }

    override fun onActivityResumed(activity: Activity) {
        Log.e("onActivityResumed", "!!")
    }

    override fun onActivityStarted(activity: Activity) {
        Log.e("onActivityStarted", "!!")
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.e("onActivityDestroyed", "!!")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.e("onActivitySaveI", "!!")
    }

    override fun onActivityStopped(activity: Activity) {
        Log.e("onActivityStopped", "!!")
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.e("onActivityCreated", "!!")
    }
}