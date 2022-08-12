package preview.android.activity.main.fragment.recommendmentor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.MainViewModel
import preview.android.activity.main.fragment.home.HomeMentorAdapter
import preview.android.activity.main.fragment.newmentor.NewMentorAdapter
import preview.android.activity.mentorinfo.MentorInfoActivity
import preview.android.activity.util.progressOff
import preview.android.activity.util.progressOn
import preview.android.data.AccountStore
import preview.android.databinding.FragmentRecommendMentorBinding

class RecommendMentorFragment : BaseFragment<FragmentRecommendMentorBinding, MainViewModel>(
    R.layout.fragment_recommend_mentor
) {
    override val vm: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.clearRecommendMentorPostList()
        vm.updateFragmentState(MainViewModel.FragmentState.recommendMentor)
        vm.getCategoryRecommendMentorPostList(AccountStore.token.value!!, "개발", 0, 20, "reviewCnt")
        progressOn(progressDialog)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        vm.getCategoryRecommendMentorPostList(
                            AccountStore.token.value!!,
                            "개발",
                            0,
                            20,
                            "reviewCnt"
                        )
                    }

                    1 -> {
                        vm.getCategoryRecommendMentorPostList(
                            AccountStore.token.value!!,
                            "경영ㆍ비즈니스",
                            0,
                            20,
                            "reviewCnt"
                        )
                    }

                    2 -> {
                        vm.getCategoryRecommendMentorPostList(
                            AccountStore.token.value!!,
                            "개발",
                            0,
                            20,
                            "reviewCnt"
                        )
                    }

                    3 -> {
                        vm.getCategoryRecommendMentorPostList(
                            AccountStore.token.value!!,
                            "개발",
                            0,
                            20,
                            "reviewCnt"
                        )
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        binding.rvRecommendMentor.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            adapter = RecommendMentorAdapter(
                onApplyButtonClicked = { mentor ->
                    val intent = Intent(context, MentorInfoActivity::class.java)
                    intent.putExtra("mentorInfo", mentor)
                    startActivity(intent)
                }, onFavoriteButtonChecked = { isChecked, postId ->
                    if (isChecked) {
                        vm.likePost(AccountStore.token.value!!, postId)
                        Log.e("CHECKED", "!!")
                    } else {
                        vm.unLikePost(AccountStore.token.value!!, postId)
                        Log.e("NOT CHECKED", "!!")
                    }

                }
            ).apply {
                submitList(vm.recommendMentorPostList.value)
            }
        }

        val searchOnClickListener = object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (view == binding.layoutSearch || view == binding.ibSearch) {
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

        binding.rvRecommendMentor.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!binding.rvRecommendMentor.canScrollVertically(1)) {
                    val size =
                        (binding.rvRecommendMentor.adapter as RecommendMentorAdapter).currentList.size
                    Log.e("currentsize", size.toString())
                    //if(size % 20 == 0 && size != 0) {
                    if (size != 0) {
                        vm.getCategoryRecommendMentorPostList(
                            AccountStore.token.value!!,
                            binding.tabLayout.getTabAt(binding.tabLayout.selectedTabPosition)!!.text.toString(),
                            size / 20,
                            20,
                            "reviewCnt"
                        )
                        progressOn(progressDialog)
                    }
                }
            }
        })


        binding.layoutSearch.setOnClickListener(searchOnClickListener)
        binding.ibSearch.setOnClickListener(searchOnClickListener)

        vm.recommendMentorPostList.observe(viewLifecycleOwner) { list ->
            Log.e("recommendMentorPostList", list.toString())
            (binding.rvRecommendMentor.adapter as RecommendMentorAdapter).submitList(list.toMutableList())
            progressOff(progressDialog)
        }

        vm.checkRecommendMentorListEnd.observe(viewLifecycleOwner) { listResponse ->
            if (listResponse == "isEmpty") {
                Toast.makeText(context, "더 이상 불러올 정보가 없습니다.", Toast.LENGTH_LONG).show()
            } else {
                progressOff(progressDialog)
            }
        }
    }
}