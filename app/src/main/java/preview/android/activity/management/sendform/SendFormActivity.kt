package preview.android.activity.management.sendform

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.management.sendform.fragment.SendFormDetailFragment
import preview.android.data.AccountStore
import preview.android.databinding.ActivitySendFormBinding

@AndroidEntryPoint
class SendFormActivity : BaseActivity<ActivitySendFormBinding, SendFormViewModel>(
    R.layout.activity_send_form
) {

    override val vm: SendFormViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.getSendForms(AccountStore.token.value!!)

        binding.rvSendForm.run {
            // setHasFixedSize(true)
            // setItemViewCacheSize(10)
            adapter = SendFormAdapter(
                onClicked = { formId ->
                    val bundle = Bundle()
                    bundle.putInt("formId", formId)
                    val fragment = SendFormDetailFragment()
                    fragment.arguments = bundle

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_send_form, fragment)
                        .commit()
                }
            ).apply {
                submitList(vm.sendFormThumbnailList.value)
            }
        }

        vm.sendFormThumbnailList.observe(this) { list ->
            Log.e("formThumbnailList", list.toString())
            (binding.rvSendForm.adapter as SendFormAdapter).submitList(list.toMutableList())
        }
    }
}