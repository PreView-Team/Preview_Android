package preview.android.activity.community

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.databinding.FragmentBoardBinding


class BoardFragment : BaseFragment<FragmentBoardBinding, CommunityHomeViewModel>(
    R.layout.fragment_board
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override val vm: CommunityHomeViewModel by viewModels()

}