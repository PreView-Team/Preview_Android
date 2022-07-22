package preview.android.activity.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseFragment
import preview.android.R
import preview.android.databinding.FragmentInfoInputBinding
import preview.android.model.Account

@AndroidEntryPoint
class InfoInputFragment : BaseFragment<FragmentInfoInputBinding, LoginViewModel>(
    R.layout.fragment_info_input
) {
    override val vm: LoginViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNickname.setOnClickListener {
            vm.checkNickname(binding.etNickname.text.toString())
            vm.nicknameResponseResult.observe(viewLifecycleOwner) {
                when (vm.loadNicknameResponseResult()) {
                    "true" -> Toast.makeText(activity, "사용 불가능한 닉네임입니다.", Toast.LENGTH_SHORT).show()
                    "false" -> {
                        Toast.makeText(activity, "사용 가능한 닉네입입니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        binding.btnNext.setOnClickListener {
            val account = vm.loadAccount().copy(
                nickname = binding.etNickname.text.toString(),
                jobNames = listOf("마케터", "프로그래머")
            )
            vm.signUp(account)
            val loginActivity = activity as LoginActivity
            loginActivity.changeFragment(2)
        }

    }
}