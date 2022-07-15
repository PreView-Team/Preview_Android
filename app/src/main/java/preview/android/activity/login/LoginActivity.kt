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
import preview.android.activity.api.dto.LoginResponse
import preview.android.activity.api.dto.LoginData
import preview.android.activity.main.MainActivity
import preview.android.databinding.ActivityLoginBinding

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(
    R.layout.activity_login
) {
    override val vm: LoginViewModel by viewModels()
    private lateinit var infoInputFragment: InfoInputFragment

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
            //vm.signUp(account)
        }

        vm.responseResult.observe(this) { responseResult ->

            when (responseResult) {
                "Success" -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.layoutLogin.id, infoInputFragment).commit()
                }
                "Fail" -> {
                    Log.d("LOGIN/FAILURE", "로그인을 실패했습니다.")
                }
            }
        }
    }
}