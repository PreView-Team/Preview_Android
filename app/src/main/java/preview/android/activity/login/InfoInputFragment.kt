package preview.android.activity.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.databinding.FragmentInfoInputBinding

class InfoInputFragment : BaseFragment<FragmentInfoInputBinding, LoginViewModel>(
    R.layout.fragment_info_input
) {
    override val vm: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}