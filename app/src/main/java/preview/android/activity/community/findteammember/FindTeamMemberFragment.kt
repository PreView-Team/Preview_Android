package preview.android.activity.community.findteammember

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.community.CommunityHomeViewModel
import preview.android.databinding.FragmentFindTeamMemberBinding


class FindTeamMemberFragment : BaseFragment<FragmentFindTeamMemberBinding, CommunityHomeViewModel>(
    R.layout.fragment_find_team_member
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override val vm: CommunityHomeViewModel by viewModels()

}