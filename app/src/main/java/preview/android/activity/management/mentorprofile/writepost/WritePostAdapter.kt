package preview.android.activity.management.mentorprofile.writepost

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import preview.android.databinding.ItemWrietPostBinding
import preview.android.model.ReceivePost

class WritePostAdapter(private val onClicked : (Int) -> Unit
) : ListAdapter<ReceivePost, WritePostAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWrietPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: ItemWrietPostBinding,
        private val onClicked : (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(receivePost: ReceivePost) {
            binding.receivepost = receivePost

            binding.layoutReceiveFormThumbnail.setOnClickListener {
                onClicked(receivePost.postId)
            }
        }
    }
    private companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ReceivePost>() {
            override fun areContentsTheSame(oldItem: ReceivePost, newItem: ReceivePost) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: ReceivePost, newItem: ReceivePost) =
                oldItem == newItem
        }
    }
}