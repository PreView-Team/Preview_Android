package preview.android.activity.management.mentorprofile.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import preview.android.activity.util.changeWordColor
import preview.android.databinding.ItemChatRoomBinding
import preview.android.model.ChatRoom

class ChatRoomAdpater(
    private val onClicked: (ChatRoom) -> Unit
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
        private val onClicked: (ChatRoom) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatRoom: ChatRoom) {
            binding.chatroom = chatRoom
            binding.layoutChatRoom.setOnClickListener {
                onClicked(chatRoom)
            }

            // TODO: 색상변경
//            if (binding.tvNickname.text == null){
//
//            }
//            else if (binding.tvNickname.text == ""){
//
//            }
//            else {
//                binding.tvNickname.text = changeWordColor(binding.tvNickname, " 멘티", "point")
//                binding.tvNickname.text = changeWordColor(binding.tvNickname, " 멘토", "skyblue")
//            }


            var lastChat = ""
            var lastTime = ""
            chatRoom.chatList.last { message ->
                lastChat = message.message
                lastTime = message.time.split("분")[0]
                true
            }
            binding.tvTime.text = lastTime
            binding.tvLastchat.text = lastChat
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