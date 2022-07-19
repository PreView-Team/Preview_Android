package preview.android.activity.main.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import preview.android.databinding.ItemMentorThumbnailBinding
import preview.android.model.Mentor

class HomeMentorAdapter : ListAdapter<Mentor, HomeMentorAdapter.ViewHolder>(diffUtil) {

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
        fun bind(mentor: Mentor) {
            binding.mentor = mentor
        }
    }

    private companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Mentor>() {
            override fun areContentsTheSame(oldItem: Mentor, newItem: Mentor) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Mentor, newItem: Mentor) =
                oldItem == newItem
        }
    }
}