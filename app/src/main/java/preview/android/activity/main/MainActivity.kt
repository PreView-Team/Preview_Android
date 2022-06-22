package preview.android.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import jkey20.errs.base.BaseActivity
import preview.android.R
import preview.android.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main) {

    override val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.errorBtn.setOnClickListener{
            throw Exception("Error Test")
        }
    }

}