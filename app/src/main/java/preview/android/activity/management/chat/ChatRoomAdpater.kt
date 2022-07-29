package preview.android.activity.management.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import preview.android.databinding.ItemChatRoomBinding

class ChatRoomAdpater(
    private val onClicked : (String) -> Unit
) : ListAdapter<String, ChatRoomAdpater.ViewHolder>(diffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemChatRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: ItemChatRoomBinding,
        private val onClicked : (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(nickname: String) {
            binding.nickname = nickname
            binding.layoutChatRoom.setOnClickListener {
                onClicked(nickname)
            }
        }
    }

    private companion object {
        val diffUtil = object : DiffUtil.ItemCallback<String>() {
            override fun areContentsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem
        }
    }
}