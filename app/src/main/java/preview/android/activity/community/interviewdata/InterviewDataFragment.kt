package preview.android.activity.community.interviewdata

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.community.CommunityHomeViewModel
import preview.android.databinding.FragmentInterviewDataBinding


class InterviewDataFragment : BaseFragment<FragmentInterviewDataBinding, CommunityHomeViewModel>(
    R.layout.fragment_interview_data
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override val vm: CommunityHomeViewModel by viewModels()

}