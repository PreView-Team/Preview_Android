package preview.android.activity.management.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.api.dto.EditNickname
import preview.android.activity.api.dto.EditUserData
import preview.android.activity.login.LoginViewModel
import preview.android.data.AccountStore
import preview.android.databinding.ActivityProfileBinding

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding, LoginViewModel>(
    R.layout.activity_profile
) {

    override val vm: LoginViewModel by viewModels()
    private val jobList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 닉네임 설정 -> 중복확인
        binding.textInputLayoutNickname.setEndIconOnClickListener {
            vm.checkNickname(binding.etNickname.text.toString())
        }

        vm.nicknameResponseResult.observe(this) { result ->
            when (result) {
                "true" -> {
                    Toast.makeText(this, "사용 불가능한 닉네임입니다.", Toast.LENGTH_SHORT).show()
                }
                "false" -> {
                    Toast.makeText(this, "사용 가능한 닉네입입니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        vm.editUserResponseResult.observe(this){ result ->
            when (result) {
                "200" -> {
                    Toast.makeText(this, "프로필 수정 완료", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "프로필 수정 불가", Toast.LENGTH_SHORT).show()
                }
            }
        }


        binding.btnApplyJobs.setOnClickListener {
            if (binding.checkDesign.isChecked) {
                jobList.add(binding.checkDesign.text.toString())
            }
            if (binding.checkMarketing.isChecked) {
                jobList.add(binding.checkMarketing.text.toString())
            }
            if (binding.checkPm.isChecked) {
                jobList.add(binding.checkPm.text.toString())
            }
            if (binding.checkProgramming.isChecked) {
                jobList.add(binding.checkProgramming.text.toString())
            }

            vm.editUser(AccountStore.token.value!!, EditUserData(jobList, binding.etNickname.text.toString()))
        }

    }
}
