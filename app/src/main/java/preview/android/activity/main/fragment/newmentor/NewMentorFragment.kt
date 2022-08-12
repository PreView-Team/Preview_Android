package preview.android.activity.main.fragment.newmentor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_new_mentor.*
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.MainViewModel
import preview.android.activity.main.fragment.home.HomeMentorAdapter
import preview.android.activity.mentorinfo.MentorInfoActivity
import preview.android.activity.review.ReviewActivity
import preview.android.activity.util.progressOff
import preview.android.activity.util.progressOn
import preview.android.data.AccountStore
import preview.android.databinding.FragmentNewMentorBinding


class NewMentorFragment : BaseFragment<FragmentNewMentorBinding, MainViewModel>(
    R.layout.fragment_new_mentor
) {

    override val vm: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.clearNewMentorPostList()
        vm.updateFragmentState(MainViewModel.FragmentState.newMentor)
        vm.getCategoryNewMentorPostList(AccountStore.token.value!!, "개발", 0, 20, "id")
        progressOn(progressDialog)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        Log.e("Tab 0","!!")
                        vm.clearNewMentorPostList()
                        vm.getCategoryNewMentorPostList(
                            AccountStore.token.value!!,
                            "개발",
                            0,
                            20,
                            "createdDate"
                        )

                    }
                    1 -> {
                        Log.e("Tab 1","!!")
                        vm.clearNewMentorPostList()
                        vm.getCategoryNewMentorPostList(
                            AccountStore.token.value!!,
                            "경영ㆍ비즈니스",
                            0,
                            20,
                            "createdDate"
                        )
                    }

                    2 -> {
                        Log.e("Tab 2","!!")
                        vm.clearNewMentorPostList()
                        vm.getCategoryNewMentorPostList(
                            AccountStore.token.value!!,
                            "마케팅",
                            0,
                            20,
                            "createdDate"
                        )
                    }
                    3 -> {
                        Log.e("Tab 3","!!")
                        vm.clearNewMentorPostList()
                        vm.getCategoryNewMentorPostList(
                            AccountStore.token.value!!,
                            "디자인",
                            0,
                            20,
                            "createdDate"
                        )
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
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
                    } else {
                        vm.unLikePost(AccountStore.token.value!!, postId)
                    }

                }
            ).apply {
                submitList(vm.newMentorPostList.value)
            }
        }

        val searchOnClickListener = object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (view == binding.layoutSearch || view == binding.ibSearch) {
                    vm.clearNewMentorPostList()
                    vm.searhMentors(
                        AccountStore.token.value!!,
                        binding.etSearch.text.toString(),
                        binding.tabLayout.getTabAt(binding.tabLayout.selectedTabPosition)!!.text.toString(),
                        0,
                        20
                    )
                }
            }

        }

        binding.rvNewMentor.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!binding.rvNewMentor.canScrollVertically(1)) {

                    val size = (binding.rvNewMentor.adapter as NewMentorAdapter).currentList.size
                    //if (size % 20 == 0 && size != 0) {
                    if (size != 0) {
                        Log.e("currentsize", size.toString())
                        vm.getCategoryNewMentorPostList(
                            AccountStore.token.value!!,
                            binding.tabLayout.getTabAt(binding.tabLayout.selectedTabPosition)!!.text.toString(),
                            size / 20,
                            20,
                            "createdDate"
                        )
                        progressOn(progressDialog)
                    }
                }
            }
        })

        binding.layoutSearch.setOnClickListener(searchOnClickListener)
        binding.ibSearch.setOnClickListener(searchOnClickListener)

        vm.newMentorPostList.observe(viewLifecycleOwner) { list ->
            Log.e("newMentorPostList", list.toString())
            (binding.rvNewMentor.adapter as NewMentorAdapter).submitList(list.toMutableList())
            progressOff(progressDialog)
        }

        vm.checkNewMentorListEnd.observe(viewLifecycleOwner) { listResponse ->
            if (listResponse == "isEmpty") {
                Toast.makeText(context, "더 이상 불러올 정보가 없습니다.", Toast.LENGTH_LONG).show()
                progressOff(progressDialog)
            } else {
                progressOff(progressDialog)
            }
        }
    }


}