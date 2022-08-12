package preview.android.activity.mentorinfo.writeform

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.mentorinfo.MentorInfoViewModel
import preview.android.activity.util.getJobList
import preview.android.data.AccountStore
import preview.android.data.MentorInfoStore
import preview.android.databinding.FragmentWriteForm1stBinding
import preview.android.model.Form


class WriteForm1stFragment : BaseFragment<FragmentWriteForm1stBinding, MentorInfoViewModel>(
    R.layout.fragment_write_form_1st
) {
    override val vm: MentorInfoViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = listOf("서울", "경기", "인천", "대전")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_jobnames, items)
        binding.tfArea.setAdapter(adapter)

        val jobItems = getJobList()
        val jobAdapter = ArrayAdapter(requireContext(), R.layout.item_jobnames, jobItems)
        binding.tfJobnames.setAdapter(jobAdapter)

        binding.etContact.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        binding.tvName.text = AccountStore.menteeNickname.value!!

        binding.btnApply.setOnClickListener {
            if (binding.etContact.text.toString().isNotEmpty()
                && binding.tfArea.text.toString().isNotEmpty()
                && binding.tfJobnames.text.toString().isNotEmpty()
            ) {
                // TODO: metnorInfoStore에 현재값 저장
                MentorInfoStore.updateForm(
                    Form(
                        postId = MentorInfoStore.mentorPost.value!!.postId,
                        name = AccountStore.menteeNickname.value!!, // 멘티값 고정
                        phoneNumber = binding.etContact.text.toString(),
                        local = binding.tfArea.text.toString(),
                        jobNames = binding.tfJobnames.text.toString()
                    )
                )
                view.findNavController()
                    .navigate(R.id.action_writeForm1stFragment_to_writeForm2ndFragment)
            } else {
                Toast.makeText(requireContext(), "모든 입력을 마치고 다시 해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}