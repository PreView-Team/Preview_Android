package preview.android.activity.mentorinfo.metorinfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import preview.android.databinding.ItemReviewBinding
import preview.android.model.Review

class ReviewAdapter : ListAdapter<Review, ReviewAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: ItemReviewBinding

        ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.review = review
            binding.ratingBar.rating = review.grade.toFloat()
        }
    }
    private companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Review>() {
            override fun areContentsTheSame(oldItem: Review, newItem: Review) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Review, newItem: Review) =
                oldItem == newItem
        }
    }
}