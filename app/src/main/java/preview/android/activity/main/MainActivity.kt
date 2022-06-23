package preview.android.activity.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import jkey20.errs.base.BaseActivity
import preview.android.R
import preview.android.databinding.ActivityMainBinding
import java.lang.RuntimeException

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main
) {

    override val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.errorBtn.setOnClickListener {
            throw RuntimeException("Error test")
        }
    }


}