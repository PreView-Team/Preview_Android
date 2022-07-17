package preview.android.activity.home

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.fragment.app.viewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.util.createMentorList
import preview.android.databinding.FragmentHomeBinding
import java.lang.RuntimeException

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    R.layout.fragment_home
) {

    override val vm: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDescription.text = setSpecificTextColor()

        binding.rvNewMentor.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            addItemDecoration(HomeMentorDecoration(context))
            adapter = HomeMentorAdapter(

            ).apply {
                submitList(createMentorList())
            }
        }

        binding.rvRecommendMentor.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            addItemDecoration(HomeMentorDecoration(context))
            adapter = HomeMentorAdapter(

            ).apply {
                submitList(createMentorList())
            }
        }
    }


    private fun setSpecificTextColor(): SpannableString {
        val description = binding.tvDescription.text
        val spannableString = SpannableString(description)
        val word = "매칭활동"
        val start = description.indexOf(word)
        val end = start + word.length

        spannableString.setSpan(
            ForegroundColorSpan(Color.parseColor("#2E90FA")),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }
}