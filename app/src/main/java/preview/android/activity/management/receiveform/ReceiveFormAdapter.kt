package preview.android.activity.management.receiveform

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import preview.android.databinding.ItemMentorBinding
import preview.android.model.MentorPost

class ReceiveFormAdapter(
    private val onApplyButtonClicked : (MentorPost) -> Unit,
    private val onFavoriteButtonChecked : (Boolean, Int) -> Unit,
) : ListAdapter<MentorPost, ReceiveFormAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMentorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onApplyButtonClicked, onFavoriteButtonChecked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: ItemMentorBinding,
        private val onApplyButtonClicked: (MentorPost) -> Unit,
        private val onFavoriteButtonChecked : (Boolean, Int) -> Unit,

    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mentorPost: MentorPost) {
            binding.mentorpost = mentorPost
            binding.btnApply.setOnClickListener {
                onApplyButtonClicked(mentorPost)
            }
            binding.ibFavorite.setOnCheckedChangeListener { button, isChecked ->
                onFavoriteButtonChecked(isChecked, mentorPost.postId)
            }
        }
    }
    private companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MentorPost>() {
            override fun areContentsTheSame(oldItem: MentorPost, newItem: MentorPost) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: MentorPost, newItem: MentorPost) =
                oldItem == newItem
        }
    }
}