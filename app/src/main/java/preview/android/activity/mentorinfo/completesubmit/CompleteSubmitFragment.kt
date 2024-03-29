package preview.android.activity.mentorinfo.completesubmit

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.MainActivity
import preview.android.activity.management.sendform.SendFormActivity
import preview.android.activity.mentorinfo.MentorInfoViewModel
import preview.android.activity.util.changeWordColor
import preview.android.databinding.FragmentCompleteSubmitBinding


class CompleteSubmitFragment : BaseFragment<FragmentCompleteSubmitBinding, MentorInfoViewModel>(
    R.layout.fragment_complete_submit
) {
    override val vm: MentorInfoViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDescription.text = changeWordColor(binding.tvDescription, "완료", "point")

        binding.btnCheckForm.setOnClickListener {
            val intent = Intent(context, SendFormActivity::class.java)
            startActivity(intent)
        }

        binding.btnHome.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }
}