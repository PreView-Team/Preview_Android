package preview.android.activity.main.fragment.recommendmentor

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.fragment.home.HomeViewModel
import preview.android.activity.mentorinfo.MentorInfoActivity
import preview.android.data.MentorStore
import preview.android.databinding.FragmentRecommendMentorBinding

class RecommendMentorFragment : BaseFragment<FragmentRecommendMentorBinding, HomeViewModel>(
    R.layout.fragment_recommend_mentor
) {
    override val vm: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                submitList(MentorStore.recommendMentorPostList.value)
            }
        }
    }
}