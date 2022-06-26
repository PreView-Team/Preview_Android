package preview.android.activity.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(
    R.layout.activity_main
) {

    override val vm: MainViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController =
            (supportFragmentManager.findFragmentById(R.id.fcv_fragment) as NavHostFragment).navController
        binding.bnvMain.setupWithNavController(navController)
    }


}