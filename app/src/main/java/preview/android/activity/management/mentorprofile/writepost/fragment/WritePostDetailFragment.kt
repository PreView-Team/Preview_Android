package preview.android.activity.management.mentorprofile.writepost.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.management.mentorprofile.writepost.WritePostViewModel
import preview.android.data.AccountStore
import preview.android.databinding.FragmentReceiveFormDetailBinding
import preview.android.databinding.FragmentWritePostDetailBinding
import preview.android.model.EditPost

class WritePostDetailFragment : BaseFragment<FragmentWritePostDetailBinding, WritePostViewModel>(
    R.layout.fragment_write_post_detail
) {
    override val vm: WritePostViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments

        vm.getWritePostDetail(
            AccountStore.token.value!!,
            bundle!!.getInt("postId")
        )

        binding.btnEdit.setOnClickListener {
            vm.editWritePostDetail(AccountStore.token.value!!, EditPost(postId = bundle!!.getInt("postId"), title = binding.etTitle.text.toString(),  contents = binding.etContents.text.toString()))
        }
        binding.btnCancel.setOnClickListener {
            vm.deleteWritePostDetail(AccountStore.token.value!!, bundle!!.getInt("postId"))
        }
    }
}