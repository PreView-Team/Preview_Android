package preview.android.activity.management.mentorprofile.editprofile

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.login.LoginViewModel
import preview.android.activity.util.getJobList
import preview.android.data.AccountStore
import preview.android.databinding.ActivityEditProfileBinding
import preview.android.model.EditMentorInfo

@AndroidEntryPoint
class EditProfileActivity : BaseActivity<ActivityEditProfileBinding, LoginViewModel>(
    R.layout.activity_edit_profile
) {
    override val vm: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val items = getJobList()
        val adapter = ArrayAdapter(this, R.layout.item_jobnames, items)

        binding.etName.setText(AccountStore.mentorNickname.value!!)
        binding.tfJob.setAdapter(adapter)
        binding.textInputLayoutNickname.setEndIconOnClickListener {

        }


        binding.btnEditProfile.setOnClickListener {
            val list = mutableListOf<String>()
            list.add(binding.tfJob.text.toString())
            vm.editMentorInfo(AccountStore.token.value!!, EditMentorInfo(nickname = binding.etName.text.toString(), jobDtoSet = list))
        }

        vm.mentorNicknameEditResult.observe(this) { result ->
            when (result) {
                "200" -> {
                    Toast.makeText(this, "프로필 수정 완료", Toast.LENGTH_SHORT).show()
                    AccountStore.updateMentorNickname(binding.etName.text.toString())
                }
                else -> {
                    Toast.makeText(this, "프로필 수정 불가", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}