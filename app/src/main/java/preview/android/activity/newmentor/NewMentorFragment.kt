package preview.android.activity.newmentor

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.home.HomeMentorAdapter
import preview.android.activity.home.HomeViewModel
import preview.android.activity.mentorinfo.MentorInfoActivity
import preview.android.activity.util.createMentorList
import preview.android.databinding.FragmentNewMentorBinding


class NewMentorFragment : BaseFragment<FragmentNewMentorBinding, HomeViewModel>(
    R.layout.fragment_new_mentor
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

        binding.rvNewMentor.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            adapter = NewMentorAdapter(
                onApplyButtonClicked = { mentor ->
                    // 멘토 소개 페이지 이동하면서 멘토정보 넘겨줌
                    val intent = Intent(context, MentorInfoActivity::class.java)
                    startActivity(intent)
                }
            ).apply {
                submitList(createMentorList())
            }
        }
    }


}