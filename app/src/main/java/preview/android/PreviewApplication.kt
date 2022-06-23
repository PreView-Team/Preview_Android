package preview.android

import android.app.Application
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import preview.android.exception.PreviewExceptionHandler


@HiltAndroidApp
class PreviewApplication : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        setCrashHandler()
    }

    private fun setCrashHandler(){

        val crashlyticsExceptionHandler = Thread.getDefaultUncaughtExceptionHandler() ?: return
        Thread.setDefaultUncaughtExceptionHandler(
            PreviewExceptionHandler(this)
        )
    }
}