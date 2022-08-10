package preview.android.activity.review

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.data.AccountStore
import preview.android.databinding.ActivityReviewBinding
import preview.android.model.MentorPost
import preview.android.model.Review

@AndroidEntryPoint
class ReviewActivity : BaseActivity<ActivityReviewBinding, ReviewViewModel>(
    R.layout.activity_review
) {
    override val vm: ReviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        val mentorPost = intent.getSerializableExtra("mentorInfo") as MentorPost
        super.onCreate(savedInstanceState)

        binding.mentorpost = mentorPost

        binding.btnSubmit.setOnClickListener {
            vm.createReview(AccountStore.token.value!!, mentorPost.postId, Review(nickname = AccountStore.menteeNickname.value!!,grade = binding.ratingBar.numStars, contents = binding.etContents.text.toString()))
        }

        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, b ->
            binding.ratingBar.rating = rating
        }
    }
}