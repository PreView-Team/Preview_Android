package preview.android.activity.management.sendform.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        val bundle = this.arguments

        vm.getFormDetail(
            AccountStore.token.value!!,
            bundle!!.getInt("formId")
        )

        binding.btnDelete.setOnClickListener {
            vm.deleteForm(AccountStore.token.value!!, bundle!!.getInt("formId"))
        }

        binding.btnEdit.setOnClickListener {
            vm.editForm(AccountStore.token.value!!, bundle!!.getInt("formId"), EditForm().copy(
                name = "주이식",
                phoneNumber = "010-9557-1081",
                contents = "작성내용"
            ))
        }

        vm.formDetail.observe(viewLifecycleOwner) { formDetailResponse ->
            binding.formdetailresponse = formDetailResponse
        }
    }
}