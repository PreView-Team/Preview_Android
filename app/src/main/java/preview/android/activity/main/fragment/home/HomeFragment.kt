package preview.android.activity.main.fragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.MainViewModel
import preview.android.activity.mentorinfo.MentorInfoActivity
import preview.android.activity.util.changeWordColor
import preview.android.activity.util.getFCMToken
import preview.android.activity.util.progressOff
import preview.android.activity.util.progressOn
import preview.android.data.AccountStore
import preview.android.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>(
    R.layout.fragment_home
) {

    override val vm: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        AccountStore.updateFcmToken(getFCMToken())

        binding.tvDescription.text = changeWordColor(binding.tvDescription, "매칭활동", "skyblue")

        vm.clearRecommendMentorThumbnailList()
        vm.clearNewMentorThumbnailList()
        vm.updateFragmentState(MainViewModel.FragmentState.home)
        vm.getNewMentorThumbnailList(AccountStore.token.value!!, 0,5, "id")
        vm.getRecommendMentorThumbnailList(AccountStore.token.value!!, 0,5, "id")
        progressOn(progressDialog)

        binding.rvNewMentor.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            addItemDecoration(HomeMentorDecoration(context))
            adapter = HomeMentorAdapter(
                onThumbnailClicked = { mentorThumbnail ->
                    val intent = Intent(context, MentorInfoActivity::class.java)
                    intent.putExtra("mentorThumbnail", mentorThumbnail)
                    startActivity(intent)
                }
            ).apply {
                submitList(listOf())
            }
        }

        binding.rvRecommendMentor.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            addItemDecoration(HomeMentorDecoration(context))
            adapter = HomeMentorAdapter(
                onThumbnailClicked = { mentorThumbnail ->
                    val intent = Intent(context, MentorInfoActivity::class.java)
                    intent.putExtra("mentorThumbnail", mentorThumbnail)
                    startActivity(intent)
                }
            ).apply {
                submitList(listOf())
            }
        }
        binding.rvNewMentor.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(!binding.rvNewMentor.canScrollHorizontally(1)){
                    val size = (binding.rvNewMentor.adapter as HomeMentorAdapter).currentList.size
                    Log.e("currentsize", size.toString())
                   // if(size % 5 == 0) {
                        vm.getNewMentorThumbnailList(AccountStore.token.value!!, size / 5, 5, "id")
                        progressOn(progressDialog)
                   // }
                }
            }
        })

        binding.rvRecommendMentor.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(!binding.rvRecommendMentor.canScrollHorizontally(1)){
                    val size = (binding.rvRecommendMentor.adapter as HomeMentorAdapter).currentList.size
                    Log.e("currentsize", size.toString())
                    if(size % 5 == 0) {
                        vm.getRecommendMentorThumbnailList(
                            AccountStore.token.value!!,
                            size / 5,
                            5,
                            "id"
                        )
                        progressOn(progressDialog)
                    }
                }
            }
        })

        binding.btnNewmentorAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_newMentorFragment)
        }

        binding.btnRecommendmentorAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_recommendMentorFragment)
        }


        vm.newMentorThumbnailList.observe(viewLifecycleOwner) { list ->
            Log.e("newMentorThumbnailList observe", list.toString())
            (binding.rvNewMentor.adapter as HomeMentorAdapter).submitList(list.toMutableList())

        }
        vm.recommendMentorThumbnailList.observe(viewLifecycleOwner) { list ->
            Log.e("recommendMentorThumbnailList observe", list.toString())
             (binding.rvRecommendMentor.adapter as HomeMentorAdapter).submitList(list.toMutableList())

        }

        vm.checkNewMentorThumbnailListEnd.observe(viewLifecycleOwner) { listResponse ->
            if (listResponse == "isEmpty") {
                Toast.makeText(context, "더 이상 불러올 정보가 없습니다.", Toast.LENGTH_LONG).show()
                progressOff(progressDialog)
            } else {
                progressOff(progressDialog)
            }
        }

        vm.checkNewMentorThumbnailListEnd.observe(viewLifecycleOwner) { listResponse ->
            if (listResponse == "isEmpty") {
                Toast.makeText(context, "더 이상 불러올 정보가 없습니다.", Toast.LENGTH_LONG).show()
                progressOff(progressDialog)
            } else {
                progressOff(progressDialog)
            }
        }

        AccountStore.menteeNickname.observe(viewLifecycleOwner){
            binding.tvMenteenickname.text = it
        }


    }

}