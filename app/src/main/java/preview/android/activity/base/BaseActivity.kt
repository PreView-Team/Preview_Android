package preview.android

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.ViewModel

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseActivity<B : ViewDataBinding, VM : ViewModel>(@LayoutRes private val layoutId: Int) :
    AppCompatActivity() {

    abstract val vm: VM
    protected lateinit var binding: B
    lateinit var progressDialog : AppCompatDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = AppCompatDialog(this)
        binding = DataBindingUtil.setContentView(this, layoutId)
        with(binding) {
            lifecycleOwner = this@BaseActivity
           // setVariable(BR.vm, vm)
        }
    }
}