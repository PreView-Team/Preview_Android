package preview.android.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.MainActivity
import preview.android.activity.util.ERROR_CODE_400
import preview.android.activity.util.ERROR_UNAUTHORIZED
import preview.android.data.AccountStore
import preview.android.databinding.FragmentLoginBinding

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>(
    R.layout.fragment_login
) {
    override val vm: LoginViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnKakao.setOnClickListener {
            vm.loginKaKao(requireContext())
        }
        vm.kakaoAccount.observe(viewLifecycleOwner) { kakaoAccount ->
            vm.loginToServer(kakaoAccount)
        }

        vm.responseResult.observe(viewLifecycleOwner) { responseResult ->
            AccountStore.updateToken(responseResult)
            if (responseResult == ERROR_CODE_400) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.layout_login, InfoInputFragment()).commit()
            } else if (responseResult == ERROR_UNAUTHORIZED) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.layout_login, InfoInputFragment()).commit()
            } else {
                vm.getUserDetail(responseResult)
            }

        }

        vm.getUserInfoResponseResult.observe(viewLifecycleOwner) { getUserInfoResponse ->

            AccountStore.updateMenteeNickname(getUserInfoResponse.nickname)
            AccountStore.updateMenteeJob(getUserInfoResponse.jobNames.get(0))
            if (getUserInfoResponse.isMentored) {
                AccountStore.updateIsMentored(getUserInfoResponse.isMentored)
                vm.getMentorInfo(AccountStore.token.value!!)
            }
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
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
        vm.getMentorInfoResponseResult.observe(viewLifecycleOwner) {
            AccountStore.updateMentorNickname(it.nickname)
            AccountStore.updateMentorJob(it.jobNames.get(0))
        }
    }
}