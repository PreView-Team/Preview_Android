package preview.android.activity.management.receiveform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.main.fragment.newmentor.NewMentorAdapter
import preview.android.activity.mentorinfo.MentorInfoActivity
import preview.android.data.AccountStore
import preview.android.databinding.ActivityReceiveFormBinding

@AndroidEntryPoint
class ReceiveFormActivity : BaseActivity<ActivityReceiveFormBinding, ReceiveFormViewModel>(
    R.layout.activity_receive_form
) {
    override val vm: ReceiveFormViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding.rvReceiveForm.run {
//            setHasFixedSize(true)
//            setItemViewCacheSize(10)
//            adapter = ReceiveFormAdapter(
//            ).apply {
//                submitList()
//            }
//        }
    }
}