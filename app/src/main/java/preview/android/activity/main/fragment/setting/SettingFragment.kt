package preview.android.activity.main.fragment.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.login.LoginViewModel
import preview.android.activity.main.MainViewModel
import preview.android.activity.management.menteechat.MenteeChatActivity
import preview.android.activity.management.mentorprofile.MentorProfileActivity
import preview.android.activity.management.mentorprofile.chat.ChatActivity
import preview.android.activity.management.profile.ProfileActivity
import preview.android.activity.management.sendform.SendFormActivity
import preview.android.activity.management.signout.SignoutActivity
import preview.android.activity.splash.SplashActivity
import preview.android.activity.util.checkLogoutProgressOn
import preview.android.databinding.FragmentSettingBinding

class SettingFragment : BaseFragment<FragmentSettingBinding, MainViewModel>(
    R.layout.fragment_setting
) {
    override val vm: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vm_login: LoginViewModel by activityViewModels()

        vm.updateFragmentState(MainViewModel.FragmentState.setting)


        binding.layoutLogout.setOnClickListener {
            checkLogoutProgressOn(requireContext(), btnOkClicked = {
                val pref = requireActivity().getSharedPreferences(
                    "loginAccount",
                    AppCompatActivity.MODE_PRIVATE
                )
                val edit = pref?.edit()
                edit?.clear()
                edit?.commit()
                startActivity(Intent(requireContext(), SplashActivity::class.java))
            })
        }

        binding.layoutSignout.setOnClickListener {
            startActivity(Intent(requireContext(), SignoutActivity::class.java))
        }
        binding.layoutProfile.setOnClickListener {
            val intent = Intent(context, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.layoutSendForm.setOnClickListener {
            val intent = Intent(context, SendFormActivity::class.java)
            startActivity(intent)
        }

        binding.layoutChat.setOnClickListener {
            val intent = Intent(context, MenteeChatActivity::class.java)
            startActivity(intent)
        }

        binding.layoutMentorprofile.setOnClickListener {
            val intent = Intent(context, MentorProfileActivity::class.java)
            startActivity(intent)
        }
    }
}