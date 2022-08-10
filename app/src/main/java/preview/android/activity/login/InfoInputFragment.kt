package preview.android.activity.login

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.MainActivity
import preview.android.activity.util.ERROR_CODE_400
import preview.android.activity.util.ERROR_UNAUTHORIZED
import preview.android.data.AccountStore
import preview.android.databinding.FragmentInfoInputBinding
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import preview.android.activity.util.getJobList

@AndroidEntryPoint
class InfoInputFragment : BaseFragment<FragmentInfoInputBinding, LoginViewModel>(
    R.layout.fragment_info_input
) {
    override val vm: LoginViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = getJobList()
        val adapter = ArrayAdapter(requireContext(), R.layout.item_jobnames, items)
        binding.tfJobnames.setAdapter(adapter)
        binding.textInputLayoutNickname.setEndIconOnClickListener {
            vm.checkNickname(binding.etNickname.text.toString())
        }

        binding.btnNext.setOnClickListener {
            if (vm.nicknameResponseResult.value != "true") {
                Toast.makeText(activity, "닉네임 중복확인 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val account = vm.loadAccount().copy(
                    nickname = binding.etNickname.text.toString(),
                    jobNames = listOf(binding.tfJobnames.text.toString())
                )
                vm.signUp(account)
            }
        }

        vm.nicknameResponseResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                "true" -> {
                    Toast.makeText(activity, "사용 불가능한 닉네임입니다.", Toast.LENGTH_SHORT).show()
                }
                "false" -> {
                    Toast.makeText(activity, "사용 가능한 닉네임입니다.", Toast.LENGTH_SHORT).show()
                    binding.textInputLayoutNickname.setEndIconTintList(
                        ColorStateList.valueOf(
                            getResources().getColor(R.color.orange)
                        )
                    )
                }
            }
        }

        vm.signUpResponseResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                "success" -> {
                    // AccountStore.updateMenteeNickname(vm.loadAccount().nickname)
                    vm.loginToServer(vm.loadAccount())
                }
                else -> {
                    Toast.makeText(activity, "회원가입에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        vm.responseResult.observe(viewLifecycleOwner) { responseResult ->
            AccountStore.updateToken(responseResult)
            if (responseResult == ERROR_CODE_400) {
                Toast.makeText(activity, "토큰을 찾을 수 없습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            } else if (responseResult == ERROR_UNAUTHORIZED) {

            } else {
                vm.getUserDetail(responseResult)
            }
        }

        vm.getUserInfoResponseResult.observe(viewLifecycleOwner) { getUserInfoResponse ->
            AccountStore.updateMenteeNickname(getUserInfoResponse.nickname)
            AccountStore.updateMenteeJob(getUserInfoResponse.jobNames.get(0))

            val pref = requireActivity().getSharedPreferences(
                "loginAccount",
                AppCompatActivity.MODE_PRIVATE
            )
            val edit = pref.edit()
            val set: Set<String> = vm.kakaoAccount.value!!.jobNames.toSet()

            edit.putString("kakaoAccessToken", vm.kakaoAccount.value!!.kakaoAccessToken)
            edit.putString("nickname", vm.kakaoAccount.value!!.nickname)
            edit.putStringSet("job", set)
            edit.commit()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.layout_login, CompleteSignUpFragment()).commit()
        }
    }
}