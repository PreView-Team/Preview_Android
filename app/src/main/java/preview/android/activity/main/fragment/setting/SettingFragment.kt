package preview.android.activity.main.fragment.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.MainViewModel
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
        vm.updateFragmentState(MainViewModel.FragmentState.setting)

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
            val intent = Intent(context, ChatActivity::class.java)
            startActivity(intent)
        }

    }
}