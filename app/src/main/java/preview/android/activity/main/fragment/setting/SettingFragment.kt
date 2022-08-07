package preview.android.activity.main.fragment.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.login.LoginViewModel
import preview.android.activity.main.MainViewModel
import preview.android.activity.management.mentorprofile.MentorProfileActivity
import preview.android.activity.management.profile.ProfileActivity
import preview.android.activity.management.sendform.SendFormActivity
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

        }

//        binding.layoutSignout.setOnClickListener {
//            vm_login.signOut(AccountStore.token.value!!)
//        }
//
//        vm_login.signOutResponseResult.observe(viewLifecycleOwner){ result ->
//            when (result) {
//                200 -> {
//                    Toast.makeText(activity, "회원 탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show()
//                    activity?.finishAffinity()
//                    startActivity(Intent(context,LoginActivity::class.java))
//                    System.exit(0)
//
//                    val pref = this.activity?.getSharedPreferences("loginAccount", AppCompatActivity.MODE_PRIVATE)
//                    val edit = pref?.edit()
//                    edit?.clear()
//                    edit?.commit()
//                }
//                else -> {
//                    Toast.makeText(activity, "회원 탈퇴 불가", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
        binding.layoutProfile.setOnClickListener {
            val intent = Intent(context, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.layoutSendForm.setOnClickListener {
            val intent = Intent(context, SendFormActivity::class.java)
            startActivity(intent)
        }

        binding.layoutMentorprofile.setOnClickListener {
            val intent = Intent(context, MentorProfileActivity::class.java)
            startActivity(intent)
        }
    }
}