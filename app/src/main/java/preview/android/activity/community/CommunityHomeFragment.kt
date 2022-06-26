package preview.android.activity.community

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.community.board.BoardFragment
import preview.android.activity.community.findteammember.FindTeamMemberFragment
import preview.android.activity.community.interviewdata.InterviewDataFragment
import preview.android.activity.community.interviewreview.InterviewReviewFragment
import preview.android.databinding.FragmentCommunityHomeBinding

class CommunityHomeFragment : BaseFragment<FragmentCommunityHomeBinding, CommunityHomeViewModel>(
    R.layout.fragment_community_home
) {

    val boardFragment = BoardFragment()
    val findTeamMemberFragment = FindTeamMemberFragment()
    val interviewDataFragment = InterviewDataFragment()
    val interviewReviewFragment = InterviewReviewFragment()

    companion object {
        const val BOARD = 0
        const val FIND_TEAM_MEMBER = 1
        const val INTERVIEW_DATA = 2
        const val INTERVIEW_REVIEW = 3
    }

    override val vm: CommunityHomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeFragment(boardFragment)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    BOARD -> {
                        changeFragment(boardFragment)
                    }

                    FIND_TEAM_MEMBER -> {
                        changeFragment(findTeamMemberFragment)
                    }

                    INTERVIEW_DATA -> {
                        changeFragment(interviewDataFragment)
                    }

                    INTERVIEW_REVIEW -> {
                        changeFragment(interviewReviewFragment)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun changeFragment(fragment : Fragment){
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()

    }
}