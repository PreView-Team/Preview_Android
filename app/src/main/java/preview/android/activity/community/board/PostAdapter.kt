package preview.android.activity.community.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import preview.android.databinding.ItemPostBinding
import preview.android.model.Post

class PostAdapter : ListAdapter<Post, PostAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: ItemPostBinding

    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.post = post
        }
    }

    private companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Post>() {
            override fun areContentsTheSame(oldItem: Post, newItem: Post) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Post, newItem: Post) =
                oldItem == newItem
        }
    }
}
