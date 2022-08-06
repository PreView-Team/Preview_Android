package preview.android.activity.main.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import preview.android.databinding.ItemMentorThumbnailBinding
import preview.android.model.MentorThumbnail

class HomeMentorAdapter(
    private val onThumbnailClicked : (MentorThumbnail) -> Unit
) : ListAdapter<MentorThumbnail, HomeMentorAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMentorThumbnailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onThumbnailClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: ItemMentorThumbnailBinding,
        private val onThumbnailClicked : (MentorThumbnail) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mentorThumbnail: MentorThumbnail) {
            binding.mentorThumbnail = mentorThumbnail
            binding.cardview.setOnClickListener {
                onThumbnailClicked(mentorThumbnail)
            }
        }
    }

    private companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MentorThumbnail>() {
            override fun areContentsTheSame(oldItem: MentorThumbnail, newItem: MentorThumbnail) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: MentorThumbnail, newItem: MentorThumbnail) =
                oldItem == newItem
        }
    }
}