package preview.android.activity.main.fragment.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.login.LoginActivity
import preview.android.activity.login.LoginViewModel
import preview.android.activity.main.MainViewModel
import preview.android.data.AccountStore
import preview.android.activity.management.chat.ChatActivity
import preview.android.activity.management.profile.ProfileActivity
import preview.android.activity.management.receiveform.ReceiveFormActivity
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
        binding.layoutMentorprofile.setOnClickListener {
            vm.editMentorInfo()

           // AccountStore.updateMentorNickname(it.nickname) TODO: 멘토 닉네임 변경시 accountStore에서 멘토닉네임도 변경해줘야함
        }

        binding.layoutProfile.setOnClickListener {
            val intent = Intent(context, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.layoutReceiveForm.setOnClickListener {
            val intent = Intent(context, ReceiveFormActivity::class.java)
            startActivity(intent)
        }
        binding.layoutSendForm.setOnClickListener {
            val intent = Intent(context, SendFormActivity::class.java)
            startActivity(intent)
        }
        binding.layoutChat.setOnClickListener {
            // TODO : isMentored = true 인 경우만 들어가게
            val intent = Intent(context, ChatActivity::class.java)
            startActivity(intent)
        }
    }
}