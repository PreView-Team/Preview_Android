package preview.android.activity.management.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import preview.android.databinding.ItemChatRoomBinding
import preview.android.model.ChatRoom

class ChatRoomAdpater(
    private val onClicked : (ChatRoom) -> Unit
) : ListAdapter<ChatRoom, ChatRoomAdpater.ViewHolder>(diffUtil) {


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
        private val onClicked : (ChatRoom) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatRoom: ChatRoom) {
            binding.chatroom = chatRoom
            binding.layoutChatRoom.setOnClickListener {
                onClicked(chatRoom)
            }
        }
    }

    private companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ChatRoom>() {
            override fun areContentsTheSame(oldItem: ChatRoom, newItem: ChatRoom) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: ChatRoom, newItem: ChatRoom) =
                oldItem == newItem
        }
    }
}