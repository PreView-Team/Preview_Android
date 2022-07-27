package preview.android.activity.mentorinfo.writeform

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.mentorinfo.MentorInfoViewModel
import preview.android.activity.util.progressOff
import preview.android.activity.util.progressOn
import preview.android.data.MentorInfoStore
import preview.android.databinding.FragmentWriteForm1stBinding
import preview.android.databinding.FragmentWriteForm2ndBinding
import preview.android.model.Form


class WriteForm2ndFragment : BaseFragment<FragmentWriteForm2ndBinding, MentorInfoViewModel>(
    R.layout.fragment_write_form_2nd
) {
    override val vm: MentorInfoViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            view.findNavController().popBackStack()
        }

        binding.etContents.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tvCharNumber.text = "${p1 + 1}/500자 (공백포함 최소50자)"
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.btnApply.setOnClickListener {
            if (binding.etContents.text!!.length < 50) {
                // 50자 이상 입력하라고 안내
            } else {
                progressOn(progressDialog)
                MentorInfoStore.updateForm(
                    MentorInfoStore.form.value!!.copy(
                        contents = binding.etContents.text.toString()
                    )
                )
                vm.sendForm(MentorInfoStore.form.value!!)
            }
        }

        vm.response.observe(viewLifecycleOwner) {
            progressOff(progressDialog)
            view.findNavController()
                .navigate(R.id.action_writeForm2ndFragment_to_completeSubmitFragment)
        }
    }

}