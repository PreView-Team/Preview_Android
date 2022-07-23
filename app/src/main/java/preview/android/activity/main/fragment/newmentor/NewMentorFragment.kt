package preview.android.activity.main.fragment.newmentor

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.MainViewModel
import preview.android.activity.main.fragment.home.HomeViewModel
import preview.android.activity.mentorinfo.MentorInfoActivity
import preview.android.data.MentorStore
import preview.android.databinding.FragmentNewMentorBinding


class NewMentorFragment : BaseFragment<FragmentNewMentorBinding, MainViewModel>(
    R.layout.fragment_new_mentor
) {

    override val vm: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.updateFragmentState(MainViewModel.FragmentState.newMentor)
        vm.getCategoryMentorPostList(1)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    // 
                    0 -> {
                        vm.getCategoryMentorPostList(1)
                    }

                    1 -> {
                        vm.getCategoryMentorPostList(2)
                    }

                    2 -> {
                        vm.getCategoryMentorPostList(3)
                    }

                    3 -> {
                        vm.getCategoryMentorPostList(4)
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
                    val intent = Intent(context, MentorInfoActivity::class.java)
                    intent.putExtra("mentorInfo", mentor)
                    startActivity(intent)
                }
            ).apply {
                submitList(MentorStore.newMentorList.value)
            }
        }
    }


}