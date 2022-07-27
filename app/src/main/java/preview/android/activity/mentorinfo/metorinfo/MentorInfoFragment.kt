package preview.android.activity.mentorinfo.metorinfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.MainViewModel
import preview.android.activity.mentorinfo.MentorInfoViewModel
import preview.android.data.AccountStore
import preview.android.data.MentorInfoStore
import preview.android.databinding.FragmentMentorInfoBinding
import preview.android.model.MentorPost


class MentorInfoFragment : BaseFragment<FragmentMentorInfoBinding, MentorInfoViewModel>(
    R.layout.fragment_mentor_info
) {
    override val vm: MentorInfoViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mentorPost = requireActivity().intent.getSerializableExtra("mentorInfo") as MentorPost
        MentorInfoStore.updateMentorPost(mentorPost)

        vm.getPostDetail(AccountStore.token.value!!, MentorInfoStore.mentorPost.value!!.postId)

        binding.btnApply.setOnClickListener {
            view.findNavController().navigate(R.id.action_mentorInfoFragment_to_writeForm1stFragment)
        }

    }
}