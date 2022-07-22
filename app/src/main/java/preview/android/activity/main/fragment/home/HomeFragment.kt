package preview.android.activity.main.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.MainViewModel
import preview.android.activity.util.changeWordSkyBlueColor
import preview.android.data.MentorStore
import preview.android.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>(
    R.layout.fragment_home
) {

    override val vm: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDescription.text = changeWordSkyBlueColor(binding.tvDescription, "매칭활동")
        vm.updateFragmentState(MainViewModel.FragmentState.home)
        vm.getNewMentorList()
        vm.getRecommendMentorList()

        binding.rvNewMentor.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            addItemDecoration(HomeMentorDecoration(context))
            adapter = HomeMentorAdapter().apply {
                submitList(MentorStore.newMentorPostList.value)
            }
        }

        binding.rvRecommendMentor.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            addItemDecoration(HomeMentorDecoration(context))
            adapter = HomeMentorAdapter().apply {
                submitList(MentorStore.recommendMentorPostList.value)
            }
        }


        binding.btnNewmentorAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_newMentorFragment)
        }

        binding.btnRecommendmentorAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_recommendMentorFragment)
        }

        MentorStore.newMentorPostList.observe(viewLifecycleOwner) { list ->
            (binding.rvNewMentor.adapter as HomeMentorAdapter).submitList(list)
        }

        MentorStore.recommendMentorPostList.observe(viewLifecycleOwner) { list ->
            (binding.rvRecommendMentor.adapter as HomeMentorAdapter).submitList(list)
        }

    }

}