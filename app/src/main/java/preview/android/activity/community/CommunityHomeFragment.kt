package preview.android.activity.community

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.databinding.FragmentCommunityHomeBinding

class CommunityHomeFragment : BaseFragment<FragmentCommunityHomeBinding, CommunityHomeViewModel>(
    R.layout.fragment_community_home
) {

    override val vm: CommunityHomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}