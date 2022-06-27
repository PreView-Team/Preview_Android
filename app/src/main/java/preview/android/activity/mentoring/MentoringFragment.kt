package preview.android.activity.mentoring

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.community.board.BestPostAdapter
import preview.android.activity.util.createBestPostList
import preview.android.activity.util.createMentorList
import preview.android.databinding.FragmentMentoringBinding
import java.lang.RuntimeException

class MentoringFragment : BaseFragment<FragmentMentoringBinding, MentoringViewModel>(
    R.layout.fragment_mentoring
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDescription.text = setSpecificTextColor()
        binding.tvDescription.setOnClickListener {
            throw RuntimeException("EXCEPTION")
        }

        binding.rvNewMentor.run{
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            adapter = MentorAdapter(

            ).apply {
                submitList(createMentorList())
            }
        }

        binding.rvRecommendMentor.run{
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            adapter = MentorAdapter(

            ).apply {
                submitList(createMentorList())
            }
        }
    }

    override val vm: MentoringViewModel by viewModels()

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