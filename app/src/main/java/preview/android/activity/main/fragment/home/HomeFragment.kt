package preview.android.activity.main.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.MainViewModel
import preview.android.activity.mentorinfo.MentorInfoActivity
import preview.android.activity.util.changeWordColor
import preview.android.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>(
    R.layout.fragment_home
) {

    override val vm: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDescription.text = changeWordColor(binding.tvDescription, "매칭활동", "skyblue")

        vm.updateFragmentState(MainViewModel.FragmentState.home)
        vm.getNewMentorThumbnailList()
        vm.getRecommendMentorThumbnailList()

        binding.rvNewMentor.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            addItemDecoration(HomeMentorDecoration(context))
            adapter = HomeMentorAdapter(
                onThumbnailClicked = { mentorPost ->
                    val intent = Intent(context, MentorInfoActivity::class.java)
                    intent.putExtra("mentorInfo", mentorPost)
                    startActivity(intent)
                }
            ).apply {
                submitList(vm.newMentorThumbnailList.value)
            }
        }

        binding.rvRecommendMentor.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            addItemDecoration(HomeMentorDecoration(context))
            adapter = HomeMentorAdapter(
                onThumbnailClicked = { mentorPost ->
                    val intent = Intent(context, MentorInfoActivity::class.java)
                    intent.putExtra("mentorInfo", mentorPost)
                    startActivity(intent)
                }
            ).apply {
                submitList(vm.recommendMentorThumbnailList.value)
            }
        }


        binding.btnNewmentorAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_newMentorFragment)
        }

        binding.btnRecommendmentorAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_recommendMentorFragment)
        }


        vm.newMentorThumbnailList.observe(viewLifecycleOwner) { list ->
            (binding.rvNewMentor.adapter as HomeMentorAdapter).submitList(list)

        }
        vm.recommendMentorThumbnailList.observe(viewLifecycleOwner) { list ->
            (binding.rvRecommendMentor.adapter as HomeMentorAdapter).submitList(list)

        }

    }

}