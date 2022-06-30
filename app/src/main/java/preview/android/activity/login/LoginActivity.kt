package preview.android.activity.login

import android.os.Bundle
import androidx.activity.viewModels
import preview.android.BaseActivity
import preview.android.R
import preview.android.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding,LoginViewModel>(
    R.layout.activity_login
) {
    override val vm: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}