package preview.android.activity.management.signout

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.login.LoginViewModel
import preview.android.activity.splash.SplashActivity
import preview.android.activity.util.checkSignoutProgressOn
import preview.android.data.AccountStore
import preview.android.databinding.ActivitySignoutBinding

@AndroidEntryPoint
class SignoutActivity : BaseActivity<ActivitySignoutBinding, LoginViewModel>(
    R.layout.activity_signout
) {
    override val vm: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnOk.setOnClickListener {
            checkSignoutProgressOn(this, btnOkClicked = {
                vm.signOut(AccountStore.token.value!!)
            })
        }

        vm.signOutResponseResult.observe(this) { result ->
            when (result) {
                200 -> {
                    Toast.makeText(this, "회원 탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    this.finishAffinity()
                    val pref = getSharedPreferences("loginAccount", MODE_PRIVATE)
                    val edit = pref?.edit()
                    edit?.clear()
                    edit?.commit()
                    startActivity(Intent(this, SplashActivity::class.java))
                }
                else -> {
                    Toast.makeText(this, "회원 탈퇴 불가", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}