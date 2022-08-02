package preview.android.activity.alarm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import preview.android.activity.main.fragment.home.HomeMentorAdapter
import preview.android.databinding.ItemAlarmBinding
import preview.android.databinding.ItemMentorThumbnailBinding
import preview.android.model.Alarm
import preview.android.model.MentorPost

class AlarmAdapter(
) : ListAdapter<Alarm, AlarmAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: ItemAlarmBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(alarm: Alarm) {
            binding.alarm = alarm
        }
    }

    private companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Alarm>() {
            override fun areContentsTheSame(oldItem: Alarm, newItem: Alarm) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: Alarm, newItem: Alarm) =
                oldItem == newItem
        }
    }
}