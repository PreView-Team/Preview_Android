package preview.android.activity.management.profile

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.api.dto.EditNickname
import preview.android.activity.api.dto.EditUserData
import preview.android.activity.login.LoginViewModel
import preview.android.activity.util.getJobList
import preview.android.data.AccountStore
import preview.android.databinding.ActivityProfileBinding

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding, LoginViewModel>(
    R.layout.activity_profile
) {

    override val vm: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val items = getJobList()
        val adapter = ArrayAdapter(this, R.layout.item_jobnames, items)

        binding.etName.setText(AccountStore.menteeNickname.value!!)
        binding.tfJob.setAdapter(adapter)
        binding.textInputLayoutNickname.setEndIconOnClickListener {
            vm.checkNickname(binding.etName.text.toString())
        }

        binding.btnEditProfile.setOnClickListener {
            val list = mutableListOf<String>()
            list.add(binding.tfJob.text.toString())
            vm.editUser(
                AccountStore.token.value!!,
                EditUserData(list, binding.etName.text.toString())
            )

        }

        vm.nicknameResponseResult.observe(this) { result ->
            when (result) {
                "true" -> {
                    Toast.makeText(this, "사용 불가능한 닉네임입니다.", Toast.LENGTH_SHORT).show()
                }
                "false" -> {
                    Toast.makeText(this, "사용 가능한 닉네입입니다.", Toast.LENGTH_SHORT).show()
                    binding.textInputLayoutNickname.setEndIconTintList(
                        ColorStateList.valueOf(
                            getResources().getColor(R.color.orange)
                        )
                    )
                }
            }
        }

        vm.editUserResponseResult.observe(this) { result ->
            when (result) {
                "200" -> {
                    Toast.makeText(this, "프로필 수정 완료", Toast.LENGTH_SHORT).show()
                    AccountStore.updateMenteeNickname(binding.etName.text.toString())
                    // 채팅, 알람 다 변경해야함

                    vm.editChatRoom("30mentorNickname", "0808mentornickname")

                }
                else -> {
                    Toast.makeText(this, "프로필 수정 불가", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}
