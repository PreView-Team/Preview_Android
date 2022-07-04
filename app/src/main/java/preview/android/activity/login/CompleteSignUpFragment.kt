package preview.android.activity.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import preview.android.BaseFragment
import preview.android.R
import preview.android.databinding.FragmentCompleteSignUpBinding

class CompleteSignUpFragment : BaseFragment<FragmentCompleteSignUpBinding, LoginViewModel>(
    R.layout.fragment_complete_sign_up
) {
    override val vm: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}