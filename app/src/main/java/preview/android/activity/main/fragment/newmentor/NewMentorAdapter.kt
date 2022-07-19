package preview.android.activity.main.fragment.newmentor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import preview.android.databinding.ItemMentorBinding
import preview.android.model.Mentor

class NewMentorAdapter(
    private val onApplyButtonClicked : (Mentor) -> Unit
) : ListAdapter<Mentor, NewMentorAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMentorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onApplyButtonClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: ItemMentorBinding,
        private val onApplyButtonClicked: (Mentor) -> Unit

    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mentor: Mentor) {
            // binding.mentor = mentor
            binding.btnApply.setOnClickListener {
                onApplyButtonClicked(mentor)
            }

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