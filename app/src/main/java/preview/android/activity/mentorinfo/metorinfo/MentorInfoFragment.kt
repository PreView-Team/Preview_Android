package preview.android.activity.mentorinfo.metorinfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import preview.android.BaseFragment
import preview.android.R
import preview.android.activity.main.MainViewModel
import preview.android.databinding.FragmentMentorInfoBinding


class MentorInfoFragment : BaseFragment<FragmentMentorInfoBinding, MainViewModel>(
    R.layout.fragment_mentor_info
) {
    override val vm: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnApply.setOnClickListener {
            view.findNavController().navigate(R.id.action_mentorInfoFragment_to_writeFormFragment)
        }

    }
}