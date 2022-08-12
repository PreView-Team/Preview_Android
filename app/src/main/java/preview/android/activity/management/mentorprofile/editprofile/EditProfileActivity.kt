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
            // TODO : 멘토 닉네임 중복체크
            vm.checkOverlapMentorNickname(AccountStore.token.value!!, binding.etName.text.toString())

        }


        binding.btnEditProfile.setOnClickListener {
            if(vm.checkOverlapMentorNicknameResponse.value != "true") {
                val list = mutableListOf<String>()
                list.add(binding.tfJob.text.toString())
                vm.editMentorInfo(
                    AccountStore.token.value!!,
                    EditMentorInfo(nickname = binding.etName.text.toString(), jobDtoSet = list)
                )
            }
            else{
                Toast.makeText(this, "닉네임 중복확인 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        vm.checkOverlapMentorNicknameResponse.observe(this){ response ->
            if(response == "true"){
                Toast.makeText(this, "사용 가능한 닉네임 입니다.", Toast.LENGTH_SHORT).show()
                binding.textInputLayoutNickname.setEndIconTintList(
                    ColorStateList.valueOf(
                        getResources().getColor(R.color.orange)
                    )
                )
            }
            else{
                Toast.makeText(this, "사용 불가능한 닉네임 입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        vm.mentorNicknameEditResult.observe(this) { result ->
            when (result) {
                "멘토 업데이트 성공" -> {
                    Toast.makeText(this, "프로필 수정 완료", Toast.LENGTH_SHORT).show()
                    AccountStore.updateMentorNickname(binding.etName.text.toString())
                    // 알람 멘토꺼 업데이트
                }
                else -> {
                    Toast.makeText(this, "프로필 수정 불가", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}