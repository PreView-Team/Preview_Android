package preview.android.activity.review

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import preview.android.BaseViewModel
import preview.android.model.Review
import preview.android.repository.ReviewRepository
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : BaseViewModel() {
    fun createReview(token : String, postId : Int, review : Review) = viewModelScope.launch {
        reviewRepository.createReview(token, postId, review).collect{
            Log.e("createReview", it.toString())
        }
    }
}