package preview.android.activity.mentorinfo.metorinfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.MainViewModel
import preview.android.activity.main.fragment.recommendmentor.RecommendMentorAdapter
import preview.android.activity.mentorinfo.MentorInfoActivity
import preview.android.activity.mentorinfo.MentorInfoViewModel
import preview.android.data.AccountStore
import preview.android.data.MentorInfoStore
import preview.android.databinding.FragmentMentorInfoBinding
import preview.android.model.MentorPost
import preview.android.model.MentorThumbnail


class MentorInfoFragment : BaseFragment<FragmentMentorInfoBinding, MentorInfoViewModel>(
    R.layout.fragment_mentor_info
) {
    override val vm: MentorInfoViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (requireActivity().intent.getSerializableExtra("mentorInfo") != null) {
            val mentorPost =
                requireActivity().intent.getSerializableExtra("mentorInfo") as MentorPost
            MentorInfoStore.updateMentorPost(mentorPost)
            vm.getPostDetail(AccountStore.token.value!!, MentorInfoStore.mentorPost.value!!.postId)
        } else if (requireActivity().intent.getSerializableExtra("mentorThumbnail") != null) {
            val mentorThumbnail =
                requireActivity().intent.getSerializableExtra("mentorThumbnail") as MentorThumbnail
            vm.getPostDetail(AccountStore.token.value!!, mentorThumbnail.postId)
        }




        binding.rvReview.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = ReviewAdapter(
            ).apply {
                submitList(listOf())
            }
        }

        binding.btnApply.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_mentorInfoFragment_to_writeForm1stFragment)
        }

        vm.postDetail.observe(viewLifecycleOwner) {
            binding.postdetailresponse = it
            (binding.rvReview.adapter as ReviewAdapter).submitList(it.reviews.toMutableList())
        }
    }
}