package preview.android.activity.login

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.main.MainActivity
import preview.android.activity.util.ERROR_CODE_400
import preview.android.activity.util.ERROR_UNAUTHORIZED
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

            AccountStore.updateNickname(savedNickname)

            vm.setAccount(Account(savedKakaoAccessToken, savedNickname, savedJobs))
        }

        binding.btnKakao.setOnClickListener {
            vm.loginKaKao(this)
        }

        vm.account.observe(this) { account ->
            Log.e("ACCOUNT", account.toString())

            val edit = pref.edit()
            val set: Set<String> = account.jobNames.toSet()

            edit.putString("kakaoAccessToken", account.kakaoAccessToken)
            edit.putString("nickname", account.nickname)
            edit.putStringSet("job", set)
            edit.commit()

            AccountStore.updateNickname(account.nickname)

            vm.loginToServer(account)
        }

        vm.responseResult.observe(this) { responseResult ->
            Log.e("RESPONSE RESULT ", "!!" + responseResult)
            if (responseResult == ERROR_CODE_400) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.layout_login, InfoInputFragment()).commit()
            }
            else if(responseResult == ERROR_UNAUTHORIZED){
                supportFragmentManager.beginTransaction()
                    .replace(R.id.layout_login, InfoInputFragment()).commit()
            }
            else {
                AccountStore.updateToken(responseResult)
                startActivity(Intent(this, MainActivity::class.java))
            }

        }

    }

}