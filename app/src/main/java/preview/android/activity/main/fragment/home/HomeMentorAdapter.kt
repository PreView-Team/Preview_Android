package preview.android.activity.main.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import preview.android.databinding.ItemMentorThumbnailBinding
import preview.android.model.MentorPost

class HomeMentorAdapter : ListAdapter<MentorPost, HomeMentorAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMentorThumbnailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: ItemMentorThumbnailBinding

    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mentorPost: MentorPost) {
            binding.mentor = mentorPost
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