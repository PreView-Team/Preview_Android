package preview.android.activity.management.mentorprofile.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import preview.android.data.AccountStore
import preview.android.databinding.ItemChatAdminBinding
import preview.android.databinding.ItemChatMenteeBinding
import preview.android.databinding.ItemChatMentorBinding
import preview.android.model.MentorPost
import preview.android.model.Message

class ChatAdapter(
) : ListAdapter<Message, RecyclerView.ViewHolder>(diffUtil) {

    private var isMentored = false

    override fun getItemViewType(position: Int): Int {
        if (currentList[position].nickname == "admin") {
            return ADMIN_CHAT
        }
        if (isMentored) {
            if (currentList[position].nickname == AccountStore.mentorNickname.value) { // TODO: mentornickname과 같을때 mychat 처리 // TODO: 멘토/멘티 뭐로 설정해야하는지 확인
                return MY_CHAT
            } else {
                return OTHER_CHAT
            }
        } else {
            if (currentList[position].nickname == AccountStore.menteeNickname.value) { // TODO: mentornickname과 같을때 mychat 처리 // TODO: 멘토/멘티 뭐로 설정해야하는지 확인
                return MY_CHAT
            } else {
                return OTHER_CHAT
            }
        }
    }

    fun setMentoredTrue() {
        isMentored = true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == MY_CHAT) {
            MentorViewHolder(
                ItemChatMentorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        } else if (viewType == OTHER_CHAT) {
            MenteeViewHolder(
                ItemChatMenteeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        } else {
            AdminViewHolder(
                ItemChatAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MY_CHAT) {
            (holder as MentorViewHolder).bind(currentList[position])
        } else if (getItemViewType(position) == OTHER_CHAT) {
            (holder as MenteeViewHolder).bind(currentList[position])
        } else {
            (holder as AdminViewHolder).bind(currentList[position])
        }
    }


    class MentorViewHolder(
        private val binding: ItemChatMentorBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.message = message
        }
    }

    class MenteeViewHolder(
        private val binding: ItemChatMenteeBinding

    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.message = message
        }
    }

    class AdminViewHolder(
        private val binding: ItemChatAdminBinding

    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.message = message
        }
    }

    private companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Message>() {
            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }
        }

        private const val MY_CHAT = 1
        private const val OTHER_CHAT = 2
        private const val ADMIN_CHAT = 3
    }
}