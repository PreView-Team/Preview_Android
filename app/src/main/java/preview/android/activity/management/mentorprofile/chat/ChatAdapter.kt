package preview.android.activity.management.mentorprofile.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import preview.android.data.AccountStore
import preview.android.databinding.*
import preview.android.model.Message

class ChatAdapter(
    private val onAcceptClicked: (Message) -> Unit,
) : ListAdapter<Message, RecyclerView.ViewHolder>(diffUtil) {

    private var isMentored = false

    override fun getItemViewType(position: Int): Int {
        if (currentList[position].nickname == "admin") {
            return ADMIN_CHAT
        }

        if (isMentored) {
            if (currentList[position].nickname == AccountStore.mentorNickname.value) {
                return MY_CHAT
            } else if (currentList[position].nickname == "calendar") {
                return CALENDAR_MY_CHAT
            } else {
                return OTHER_CHAT
            }
        } else {
            if (currentList[position].nickname == AccountStore.menteeNickname.value) {
                return MY_CHAT
            } else if (currentList[position].nickname == "calendar") {
                return CALENDAR_OTHER_CHAT
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
        } else if (viewType == ADMIN_CHAT) {
            AdminViewHolder(
                ItemChatAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        } else if (viewType == CALENDAR_MY_CHAT) {
            CalendarMentorViewHolder(
                ItemChatCalendarMentorBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            CalendarMenteeViewHolder(
                ItemChatCalendarMenteeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), onAcceptClicked
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MY_CHAT) {
            (holder as MentorViewHolder).bind(currentList[position])
        } else if (getItemViewType(position) == OTHER_CHAT) {
            (holder as MenteeViewHolder).bind(currentList[position])
        } else if (getItemViewType(position) == ADMIN_CHAT) {
            (holder as AdminViewHolder).bind(currentList[position])
        } else if (getItemViewType(position) == CALENDAR_MY_CHAT) {
            (holder as CalendarMentorViewHolder).bind(currentList[position])
        } else if (getItemViewType(position) == CALENDAR_OTHER_CHAT) {
            (holder as CalendarMenteeViewHolder).bind(currentList[position])
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

    class CalendarMenteeViewHolder(
        private val binding: ItemChatCalendarMenteeBinding,
        private val onAcceptClicked: (Message) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            val value = message.message
            val dateSplit = value.split("-")[0]
            val timeSplit = value.split("-")[1]
            val date = dateSplit.split("날짜")[1]
            val time = timeSplit.split("시간")[1]
            binding.tvDate.text = date
            binding.tvTime.text = time
            binding.btnAccept.setOnClickListener {
                onAcceptClicked(message)
                Log.e("ACCEP!", "!")
            }
        }
    }

    class CalendarMentorViewHolder(
        private val binding: ItemChatCalendarMentorBinding,

    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            val value = message.message
            val dateSplit = value.split("-")[0]
            val timeSplit = value.split("-")[1]
            val date = dateSplit.split("날짜")[1]
            val time = timeSplit.split("시간")[1]
            binding.tvDate.text = date
            binding.tvTime.text = time
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
        private const val CALENDAR_MY_CHAT = 4
        private const val CALENDAR_OTHER_CHAT = 5
    }
}