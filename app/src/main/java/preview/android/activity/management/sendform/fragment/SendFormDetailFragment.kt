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
    }
}