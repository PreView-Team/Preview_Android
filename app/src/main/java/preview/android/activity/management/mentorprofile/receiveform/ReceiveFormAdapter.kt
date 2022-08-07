package preview.android.activity.management.mentorprofile.receiveform

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import preview.android.databinding.ItemReceiveFormThumbnailBinding
import preview.android.model.ReceiveFormThumbnail

class ReceiveFormAdapter(
    private val onClicked : (Int) -> Unit
) : ListAdapter<ReceiveFormThumbnail, ReceiveFormAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemReceiveFormThumbnailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: ItemReceiveFormThumbnailBinding,
        private val onClicked : (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(receiveFormThumbnail: ReceiveFormThumbnail) {
            binding.receiveformthumbnail = receiveFormThumbnail

            if (receiveFormThumbnail.status == "수락") {
                binding.tvStatus.text = "수락"
            }
            else if(receiveFormThumbnail.status == "대기"){
                binding.tvStatus.text = "대기"
            }
            else{
                binding.tvStatus.text = "거절"
            }

            binding.layoutReceiveformthumbnail.setOnClickListener {
                onClicked(receiveFormThumbnail.formId)
            }
        }
    }
    private companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ReceiveFormThumbnail>() {
            override fun areContentsTheSame(oldItem: ReceiveFormThumbnail, newItem: ReceiveFormThumbnail) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: ReceiveFormThumbnail, newItem: ReceiveFormThumbnail) =
                oldItem == newItem
        }
    }
}