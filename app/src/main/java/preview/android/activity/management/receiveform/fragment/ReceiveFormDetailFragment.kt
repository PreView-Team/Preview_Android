package preview.android.activity.management.receiveform.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.management.receiveform.ReceiveFormViewModel
import preview.android.data.AccountStore
import preview.android.databinding.FragmentReceiveFormDetailBinding


class ReceiveFormDetailFragment :
    BaseFragment<FragmentReceiveFormDetailBinding, ReceiveFormViewModel>(
        R.layout.fragment_receive_form_detail
    ) {
    override val vm: ReceiveFormViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments

        vm.getReceiveFormDetail(
            AccountStore.token.value!!,
            bundle!!.getInt("formId")
        )

        binding.btnAccept.setOnClickListener {
            vm.aceeptForm(AccountStore.token.value!!, bundle.getInt("formId"))
            vm.createRoom(vm.receiveFormDetail.value!!.username)
        }

        binding.btnRefuse.setOnClickListener {
            vm.refuseForm(AccountStore.token.value!!, bundle.getInt("formId"))
        }

        vm.receiveFormDetail.observe(viewLifecycleOwner) { receiveFormDetailResponse ->
            binding.receiveformdetail = receiveFormDetailResponse
        }
    }
}