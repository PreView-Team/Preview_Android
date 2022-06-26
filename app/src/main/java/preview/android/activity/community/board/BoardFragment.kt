package preview.android.activity.community.board

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.community.CommunityHomeViewModel
import preview.android.activity.util.createBestPostList
import preview.android.databinding.FragmentBoardBinding


class BoardFragment : BaseFragment<FragmentBoardBinding, CommunityHomeViewModel>(
    R.layout.fragment_board
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.rvBestpost.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            adapter = BestPostAdapter(

            ).apply {
                submitList(createBestPostList())
            }
        }

        binding.rvPost.run{
            setHasFixedSize(true)
            setItemViewCacheSize(10)

            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = PostAdapter(

            ).apply {
                submitList(createBestPostList())
            }
        }
    }

    override val vm: CommunityHomeViewModel by viewModels()

}