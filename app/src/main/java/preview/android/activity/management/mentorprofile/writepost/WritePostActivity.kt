package preview.android.activity.management.mentorprofile.writepost

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.management.mentorprofile.writepost.fragment.WritePostDetailFragment
import preview.android.data.AccountStore
import preview.android.databinding.ActivityWritePostBinding

@AndroidEntryPoint
class WritePostActivity : BaseActivity<ActivityWritePostBinding, WritePostViewModel>(
    R.layout.activity_write_post
) {
    override val vm: WritePostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.getWritePosts(AccountStore.token.value!!)

        binding.rvWriteForm.run {
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            adapter = WritePostAdapter(
                onClicked = { postId ->
                    val bundle = Bundle()
                    bundle.putInt("postId", postId)
                    val fragment = WritePostDetailFragment()
                    fragment.arguments = bundle

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_write_posts, fragment)
                        .commit()
                }
            ).apply {
                submitList(listOf())
            }
        }
        /*
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
         */
        vm.receivePostThumbnailList.observe(this) { list ->
            (binding.rvWriteForm.adapter as WritePostAdapter).submitList(list.toMutableList())
        }
    }
}