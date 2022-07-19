package preview.android.activity.mentorinfo.writeform

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.mentorinfo.MentorInfoViewModel
import preview.android.activity.util.progressOff
import preview.android.activity.util.progressOn
import preview.android.databinding.FragmentWriteFormBinding
import preview.android.model.Form


class WriteFormFragment : BaseFragment<FragmentWriteFormBinding, MentorInfoViewModel>(
    R.layout.fragment_write_form
) {
    override val vm: MentorInfoViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnApply.setOnClickListener {
            progressOn(progressDialog)
            vm.sendForm(Form())
        }

        vm.response.observe(viewLifecycleOwner){
            progressOff(progressDialog)
            // 다음 페이지로 이동
            view.findNavController().navigate(R.id.action_writeFormFragment_to_completeSubmitFragment)
        }
    }


}