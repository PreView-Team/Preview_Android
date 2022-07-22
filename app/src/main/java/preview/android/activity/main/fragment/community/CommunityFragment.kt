package preview.android.activity.main.fragment.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.MainViewModel
import preview.android.databinding.FragmentCommunityBinding

class CommunityFragment : BaseFragment<FragmentCommunityBinding, MainViewModel>(
    R.layout.fragment_community
) {

    override val vm: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.updateFragmentState(MainViewModel.FragmentState.community)
    }

}