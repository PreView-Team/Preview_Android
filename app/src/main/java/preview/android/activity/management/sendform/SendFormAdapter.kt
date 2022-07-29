package preview.android.activity.management.sendform

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import preview.android.databinding.ItemFormThumbnailBinding
import preview.android.model.SendFormThumbnail

class SendFormAdapter(
    private val onClicked : (Int) -> Unit
) : ListAdapter<SendFormThumbnail, SendFormAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemFormThumbnailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: ItemFormThumbnailBinding,
        private val onClicked : (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sendFormThumbnail: SendFormThumbnail) {
            Log.e("formThumbnail", "!!"+ sendFormThumbnail.toString())
            binding.formthumbnail = sendFormThumbnail
            if (sendFormThumbnail.status == "수락") {
                binding.tvStatus.text = "수락"
            }
            else if(sendFormThumbnail.status == "대기"){
                binding.tvStatus.text = "대기"
            }
            else{
                binding.tvStatus.text = "거절"
            }
            binding.layoutFormthumbnail.setOnClickListener {
                onClicked(sendFormThumbnail.formId)
            }
        }
    }

    private companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SendFormThumbnail>() {
            override fun areContentsTheSame(oldItem: SendFormThumbnail, newItem: SendFormThumbnail) =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: SendFormThumbnail, newItem: SendFormThumbnail) =
                oldItem == newItem
        }
    }
}