package preview.android.activity.management.sendform.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.management.sendform.SendFormViewModel
import preview.android.data.AccountStore
import preview.android.databinding.FragmentSendFormDetailBinding
import preview.android.model.EditForm


class SendFormDetailFragment : BaseFragment<FragmentSendFormDetailBinding, SendFormViewModel>(
    R.layout.fragment_send_form_detail
) {
    override val vm: SendFormViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = listOf("서울", "경기", "인천", "대전")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_jobnames, items)
        binding.tfArea.setAdapter(adapter)


        val jobItems = listOf("마케터", "프로그래밍")
        val jobAdapter = ArrayAdapter(requireContext(), R.layout.item_jobnames, jobItems)
        binding.tfJob.setAdapter(jobAdapter)


        val bundle = this.arguments

        vm.getFormDetail(
            AccountStore.token.value!!,
            bundle!!.getInt("formId")
        )

        binding.btnCancel.setOnClickListener {
            vm.deleteForm(AccountStore.token.value!!, bundle!!.getInt("formId"))
        }

        binding.btnEdit.setOnClickListener {
            vm.editForm(AccountStore.token.value!!, bundle!!.getInt("formId"), EditForm(
                name = binding.etName.text.toString(),
                phoneNumber = "010-9557-1081",
                contents = "작성내용"
            ))
        }

        vm.formDetail.observe(viewLifecycleOwner) { formDetailResponse ->
            binding.formdetailresponse = formDetailResponse

        }
    }
}