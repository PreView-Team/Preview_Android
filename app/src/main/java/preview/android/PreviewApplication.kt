package preview.android

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import preview.android.activity.util.getToken
import preview.android.exception.PreviewExceptionHandler


@HiltAndroidApp
class PreviewApplication : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
        setCrashHandler()
        getToken()
    }

    private fun setCrashHandler(){

        Thread.setDefaultUncaughtExceptionHandler(
            PreviewExceptionHandler(this)
        )
    }
}