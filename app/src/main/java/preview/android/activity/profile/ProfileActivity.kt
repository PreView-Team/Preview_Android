package preview.android.activity.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        //닉네임 수정 완료 버튼 클릭 시 서버 연동
        binding.btnApplyNickname.setOnClickListener {
            vm.editNickname(AccountStore.token.value!!,EditNickname(binding.etNickname.text.toString()))
        }

        vm.editNicknameResponseResult.observe(this){ result ->
            when (result) {
                200 -> {
                    Toast.makeText(this, "닉네임 수정 완료", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "닉네임 수정 불가", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val jobList = mutableListOf<String>()
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

        binding.btnApplyJobs.setOnClickListener {
            vm.editUser(AccountStore.token.value!!, EditUserData(jobList))
        }

    }
}
