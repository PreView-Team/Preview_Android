package preview.android.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.main.MainActivity
import preview.android.activity.util.ERROR_CODE_400
import preview.android.activity.util.ERROR_UNAUTHORIZED
import preview.android.data.AccountStore
import preview.android.databinding.ActivityLoginBinding

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(
    R.layout.activity_login
) {

    override val vm: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnKakao.setOnClickListener {
            vm.loginKaKao(this)
        }
        vm.kakaoAccount.observe(this) { kakaoAccount ->
            vm.loginToServer(kakaoAccount)
        }

        vm.responseResult.observe(this) { responseResult ->
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
            AccountStore.updateMenteeJob(getUserInfoResponse.jobNames.get(0))
            if (getUserInfoResponse.isMentored) {
                AccountStore.updateIsMentored(getUserInfoResponse.isMentored)
                vm.getMentorInfo(AccountStore.token.value!!)
            }
            val pref = getSharedPreferences(
                "loginAccount",
                MODE_PRIVATE
            )
            val edit = pref.edit()
            val set: Set<String> = vm.kakaoAccount.value!!.jobNames.toSet()

            edit.putString("kakaoAccessToken", vm.kakaoAccount.value!!.kakaoAccessToken)
            edit.putString("nickname", vm.kakaoAccount.value!!.nickname)
            edit.putStringSet("job", set)
            edit.commit()
            startActivity(Intent(this, MainActivity::class.java))
        }
        vm.getMentorInfoResponseResult.observe(this) {
            AccountStore.updateMentorNickname(it.nickname)
            AccountStore.updateMentorJob(it.jobNames.get(0))
        }
    }

}