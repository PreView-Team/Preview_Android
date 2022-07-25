package preview.android.activity.main.fragment.recommendmentor

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.MainViewModel
import preview.android.activity.mentorinfo.MentorInfoActivity
import preview.android.data.MentorStore
import preview.android.databinding.FragmentRecommendMentorBinding

class RecommendMentorFragment : BaseFragment<FragmentRecommendMentorBinding, MainViewModel>(
    R.layout.fragment_recommend_mentor
) {
    override val vm: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.updateFragmentState(MainViewModel.FragmentState.recommendMentor)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                    }

                    1 -> {
                    }

                    2 -> {
                    }

                    3 -> {
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        binding.rvRecommendMentor.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            adapter = RecommendMentorAdapter(
                onApplyButtonClicked = { mentor ->
                    val intent = Intent(context, MentorInfoActivity::class.java)
                    intent.putExtra("mentorInfo", mentor)
                    startActivity(intent)
                }
            ).apply {
                submitList(MentorStore.recommendMentorList.value)
            }
        }
    }
}