package preview.android.activity.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.main.MainActivity
import preview.android.activity.main.MainViewModel
import preview.android.databinding.ActivityLoginBinding

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(
    R.layout.activity_login
) {
    override val vm: LoginViewModel by viewModels()
    val main_vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var keyHash = Utility.getKeyHash(this)
        Log.e(TAG, "해시 키 값 : ${keyHash}")

        binding.btnKakao.setOnClickListener {
            vm.loginKaKao(this)
        }

        vm.account.observe(this) { account ->
            Log.e("ACCOUNT", account.toString())
            vm.loginToServer(account)
            vm.setKakaoAccessToken(account.kakaoAccessToken)
            //vm.signUp(account)
        }

        vm.responseResult.observe(this) { responseResult ->

            if (vm.loadResponseResult() == "400") {
                changeFragment(1)
            } else {
                main_vm.setToken(vm.loadResponseResult())
                startActivity(Intent(this, MainActivity::class.java))
            }

        }

    }

    fun changeFragment(index: Int) {
        val infoInputFragment = InfoInputFragment()
        val completeSignUpFragment = CompleteSignUpFragment()
        when (index) {
            1 -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.layout_login, infoInputFragment).commit()
            }
            2 -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.layout_login, completeSignUpFragment).commit()
            }
        }
    }

}