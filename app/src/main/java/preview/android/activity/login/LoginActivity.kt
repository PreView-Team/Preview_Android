package preview.android.activity.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.main.MainActivity
import preview.android.activity.util.ERROR_CODE_400
import preview.android.activity.util.ERROR_UNAUTHORIZED
import preview.android.activity.util.deviceToken
import preview.android.activity.util.getFCMToken
import preview.android.data.AccountStore
import preview.android.databinding.ActivityLoginBinding
import preview.android.model.Account

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(
    R.layout.activity_login
) {

    override val vm: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var keyHash = Utility.getKeyHash(this)
        Log.e(TAG, "해시 키 값 : ${keyHash}")


        AccountStore.updateFcmToken(getFCMToken())



        // 로그인 여부 체크는 splash에서 진행
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
        } else {
            Log.e("SPF", "저장 되어 있음 $savedKakaoAccessToken $savedNickname $savedJobs ")
            vm.setKakaoAccount(Account(savedKakaoAccessToken, savedNickname, savedJobs))
        }

        binding.btnKakao.setOnClickListener {
            vm.loginKaKao(this)
        }

        vm.kakaoAccount.observe(this) { kakaoAccount ->
            vm.loginToServer(kakaoAccount)
        }

        vm.responseResult.observe(this) { responseResult ->
            Log.e("RESPONSE RESULT ", "token: " + responseResult)

            AccountStore.updateToken(responseResult)
            if (responseResult == ERROR_CODE_400) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.layout_login, InfoInputFragment()).commit()
            } else if (responseResult == ERROR_UNAUTHORIZED) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.layout_login, InfoInputFragment()).commit()
            } else {
                vm.getUserDetail(responseResult)
            }

        }

        vm.getUserInfoResponseResult.observe(this) { getUserInfoResponse ->

            AccountStore.updateMenteeNickname(getUserInfoResponse.nickname)
            if(getUserInfoResponse.isMentored) {
                AccountStore.updateIsMentored(getUserInfoResponse.isMentored)
                vm.getMentorInfo(AccountStore.token.value!!)
            }
            val edit = pref.edit()
            val set: Set<String> = vm.kakaoAccount.value!!.jobNames.toSet()

            edit.putString("kakaoAccessToken", vm.kakaoAccount.value!!.kakaoAccessToken)
            edit.putString("nickname", vm.kakaoAccount.value!!.nickname)
            edit.putStringSet("job", set)
            edit.commit()
            val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.layout_login)
            if (fragment is InfoInputFragment) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.layout_login, CompleteSignUpFragment()).commit()
            } else {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.layout_login, InfoInputFragment()).commit()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        vm.getMentorInfoResponseResult.observe(this){
            AccountStore.updateMentorNickname(it.nickname)
        }
    }
}