package preview.android

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import preview.android.exception.PreviewExceptionHandler


@HiltAndroidApp
class PreviewApplication : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "5c9d3c8e293983327d4d012fb7ba4ab2")
        setCrashHandler()
    }

    private fun setCrashHandler(){

        val crashlyticsExceptionHandler = Thread.getDefaultUncaughtExceptionHandler() ?: return
        Thread.setDefaultUncaughtExceptionHandler(
            PreviewExceptionHandler(this)
        )
    }
}