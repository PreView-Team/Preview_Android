package preview.android.activity.main

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import preview.android.R
import preview.android.data.AccountStore
import preview.android.databinding.WriteDialogBinding
import preview.android.model.MentorPost
import preview.android.model.Writing

class WriteDialogFragment : DialogFragment() {
    lateinit var binding: WriteDialogBinding
    val vm: MainViewModel by activityViewModels()
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
            vm.setWriting(
                Writing(
                    categoryName  = AccountStore.mentorJob.value!!,
                    title = binding.etTitle.text.toString(),
                    contents = binding.etContents.text.toString()
                    // TODO : 직군 설정
                )
            )
            true
        }

        vm.sendWritingResponse.observe(this){ response ->
            if(response == "성공"){
                dismiss()
            }
            else{
                Toast.makeText(requireContext(), "글쓰기에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }
}