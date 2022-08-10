package preview.android.activity.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.MainActivity
import preview.android.data.AccountStore
import preview.android.databinding.FragmentCompleteSignUpBinding

@AndroidEntryPoint
class CompleteSignUpFragment : BaseFragment<FragmentCompleteSignUpBinding, LoginViewModel>(
    R.layout.fragment_complete_sign_up
) {
    override val vm: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.createAlarmList(AccountStore.menteeNickname.value!!)

        binding.nickname = AccountStore.menteeNickname.value
        binding.btnGoHome.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
        }

    }
}