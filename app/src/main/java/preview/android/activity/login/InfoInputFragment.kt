package preview.android.activity.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseFragment
import preview.android.R
import preview.android.data.AccountStore
import preview.android.databinding.FragmentInfoInputBinding

@AndroidEntryPoint
class InfoInputFragment : BaseFragment<FragmentInfoInputBinding, LoginViewModel>(
    R.layout.fragment_info_input
) {
    override val vm: LoginViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textInputLayoutNickname.setEndIconOnClickListener {
            vm.checkNickname(binding.etNickname.text.toString())
        }

        binding.btnNext.setOnClickListener {
            // TODO: jobNames 변경필요
            val account = vm.loadAccount().copy(
                nickname = binding.etNickname.text.toString(),
                jobNames = listOf("마케터", "프로그래머")
            )
            vm.signUp(account)
            AccountStore.updateNickname(account.nickname)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.layout_login, CompleteSignUpFragment()).commit()
        }

        vm.nicknameResponseResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                "true" -> {
                    Toast.makeText(activity, "사용 불가능한 닉네임입니다.", Toast.LENGTH_SHORT).show()
                }
                "false" -> {
                    Toast.makeText(activity, "사용 가능한 닉네입입니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}