package preview.android.activity.main.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.util.changeWordSkyBlueColor
import preview.android.data.MentorStore
import preview.android.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home
) {

    override val vm: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDescription.text = changeWordSkyBlueColor(binding.tvDescription, "매칭활동")

        binding.rvNewMentor.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            addItemDecoration(HomeMentorDecoration(context))
            adapter = HomeMentorAdapter().apply {
                submitList(MentorStore.newMentorList.value)
            }
        }

        binding.rvRecommendMentor.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            addItemDecoration(HomeMentorDecoration(context))
            adapter = HomeMentorAdapter().apply {
                submitList(MentorStore.recommendMentorList.value)
            }
        }


       // TODO: 신규멘토 전체보기, 추천멘토 전체보기 이벤트 리스너 추가해야함

        MentorStore.newMentorList.observe(viewLifecycleOwner) { list ->
            (binding.rvNewMentor.adapter as HomeMentorAdapter).submitList(list)
        }

        MentorStore.recommendMentorList.observe(viewLifecycleOwner) { list ->
            (binding.rvRecommendMentor.adapter as HomeMentorAdapter).submitList(list)
        }

    }

}