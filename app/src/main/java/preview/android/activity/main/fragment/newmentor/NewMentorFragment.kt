package preview.android.activity.main.fragment.newmentor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayout
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.MainViewModel
import preview.android.activity.mentorinfo.MentorInfoActivity
import preview.android.data.AccountStore
import preview.android.databinding.FragmentNewMentorBinding


class NewMentorFragment : BaseFragment<FragmentNewMentorBinding, MainViewModel>(
    R.layout.fragment_new_mentor
) {

    override val vm: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.updateFragmentState(MainViewModel.FragmentState.newMentor)
        vm.getCategoryNewMentorPostList(AccountStore.token.value!!, "디자인")

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    // 
                    0 -> {
                        vm.getCategoryNewMentorPostList(AccountStore.token.value!!, "프로그래밍")
                    }

                    1 -> {
                        vm.getCategoryNewMentorPostList(AccountStore.token.value!!, "PM")
                    }

                    2 -> {
                        vm.getCategoryNewMentorPostList(AccountStore.token.value!!, "1")
                    }

                    3 -> {
                        vm.getCategoryNewMentorPostList(AccountStore.token.value!!, "1")
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
                    val intent = Intent(activity, MentorInfoActivity::class.java)
                    intent.putExtra("mentorInfo", mentor)
                    startActivity(intent)
                },
                onFavoriteButtonChecked = { isChecked, postId ->
                    if (isChecked) {
                        vm.likePost(AccountStore.token.value!!, postId)
                        Log.e("CHECKED", "!!")
                    } else {
                        vm.unLikePost(AccountStore.token.value!!, postId)
                        Log.e("NOT CHECKED", "!!")
                    }

                }
            ).apply {
                submitList(vm.newMentorPostList.value)
            }
        }

        vm.newMentorPostList.observe(viewLifecycleOwner) { list ->
            Log.e("newMentorPostList", list.toString())
            (binding.rvNewMentor.adapter as NewMentorAdapter).submitList(list.toMutableList())
        }
    }


}