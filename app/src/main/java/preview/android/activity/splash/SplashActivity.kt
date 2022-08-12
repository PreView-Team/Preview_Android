package preview.android.activity.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.login.*
import preview.android.activity.main.MainActivity
import preview.android.activity.util.ERROR_CODE_400
import preview.android.activity.util.ERROR_UNAUTHORIZED
import preview.android.activity.util.changeWordColor
import preview.android.activity.util.getFCMToken
import preview.android.data.AccountStore
import preview.android.databinding.ActivitySplashBinding
import preview.android.model.Account

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, LoginViewModel>(
    R.layout.activity_splash
) {

    override val vm: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uiOptions = window.decorView.systemUiVisibility
        var newUiOptions = uiOptions
        val isImmersiveModeEnabled =
            uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY == uiOptions
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_FULLSCREEN
        newUiOptions = newUiOptions xor View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = newUiOptions

        binding.tvDescription.text = changeWordColor(binding.tvDescription, "쉬운 면접", "point")

        AccountStore.updateFcmToken(getFCMToken())

        val pref = getSharedPreferences("loginAccount", MODE_PRIVATE)

        val savedKakaoAccessToken = pref.getString("kakaoAccessToken", "").toString()
        val savedNickname = pref.getString("nickname", "").toString()
        val jobs = pref.getStringSet("job", null)
        val savedJobs = ArrayList<String>()

        if (jobs != null) {
            for (i in jobs) {
                savedJobs.add(i)
            }
        }

        if (savedKakaoAccessToken.equals("")) {
            Log.e("SharedPreferences", "저장 안됨")
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            Log.e("SPF", "저장 되어 있음 $savedKakaoAccessToken $savedNickname $savedJobs ")
            vm.setKakaoAccount(Account(savedKakaoAccessToken, savedNickname, savedJobs))
        }
        vm.kakaoAccount.observe(this) { kakaoAccount ->
            vm.loginToServer(kakaoAccount)
        }
        vm.responseResult.observe(this) { responseResult ->
            Log.e("RESPONSE RESULT ", "token: " + responseResult)

            AccountStore.updateToken(responseResult)
            if (responseResult == ERROR_CODE_400) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.layout_splash, InfoInputFragment()).commit()
            } else if (responseResult == ERROR_UNAUTHORIZED) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                vm.getUserDetail(responseResult)
            }

        }

        vm.getUserInfoResponseResult.observe(this) { getUserInfoResponse ->

            AccountStore.updateMenteeNickname(getUserInfoResponse.nickname)
            AccountStore.updateMenteeJob(getUserInfoResponse.jobNames.get(0))
            if (getUserInfoResponse.isMentored) {
                AccountStore.updateIsMentored(getUserInfoResponse.isMentored)
                vm.getMentorInfo(AccountStore.token.value!!)
            }
            val edit = pref.edit()
            val set: Set<String> = vm.kakaoAccount.value!!.jobNames.toSet()

            edit.putString("kakaoAccessToken", vm.kakaoAccount.value!!.kakaoAccessToken)
            edit.putString("nickname", vm.kakaoAccount.value!!.nickname)
            edit.putStringSet("job", set)
            edit.commit()
            startActivity(Intent(this, MainActivity::class.java))

        }
        vm.getMentorInfoResponseResult.observe(this)
        {
            Log.e("getMentorInfoResponseResult", it.toString())
            AccountStore.updateMentorNickname(it.nickname)
            AccountStore.updateMentorJob(it.jobNames.get(0))
        }
    }

}