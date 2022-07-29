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
import preview.android.activity.management.receiveform.fragment.ReceiveFormDetailFragment
import preview.android.activity.management.sendform.SendFormAdapter
import preview.android.activity.management.sendform.fragment.SendFormDetailFragment
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

        vm.getReceiveForms(AccountStore.token.value!!)

        binding.rvReceiveForm.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            adapter = ReceiveFormAdapter(
                onClicked = { formId ->
                    val bundle = Bundle()
                    bundle.putInt("formId", formId)
                    val fragment = ReceiveFormDetailFragment()
                    fragment.arguments = bundle

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_receive_form, fragment)
                        .commit()
                }
            ).apply {
                submitList(vm.receiveFormThumbnailList.value)
            }
        }
        vm.receiveFormThumbnailList.observe(this) { list ->
            Log.e("receivelList", list.toString())
            (binding.rvReceiveForm.adapter as ReceiveFormAdapter).submitList(list.toMutableList())
        }

    }
}