package preview.android.activity.main

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import preview.android.R
import preview.android.databinding.WriteDialogBinding
import preview.android.model.MentorPost

class WriteDialogFragment : DialogFragment() {
    lateinit var binding: WriteDialogBinding
    val vm : MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.write_dialog, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ibClose.setOnClickListener {
            binding.layoutWritePolicy.isGone = true
        }

        binding.tvWritePolicy.setOnClickListener {
            // link 연결
        }
        binding.tbWrite.setNavigationOnClickListener {
            dismiss()
        }
        binding.tbWrite.setOnMenuItemClickListener { menuItem ->
            // vm.setWriteMentorPost(MentorPost(title = "test0722", contents = "contents", subTitle = "123123", categoryId = 1))
            true
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
}