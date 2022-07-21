package preview.android.activity.main

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.util.changeFabClose
import preview.android.activity.util.changeFabOpen
import preview.android.activity.util.isFabOpened
import preview.android.activity.util.showDialogFragment
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

        binding.tbMain.setNavigationOnClickListener { view ->

        }

        binding.fab.setOnClickListener {
            if (isFabOpened(binding.fab)) {
                changeFabClose(binding.fab, binding.layoutFabClick)
            } else {
                changeFabOpen(binding.fab, binding.layoutFabClick)
            }
        }

        binding.btnWrite.setOnClickListener {
            Log.e("write", "!!")
            showDialogFragment(this, WriteDialogFragment())
        }
        binding.btnCheckMentor.setOnClickListener {

            Log.e("check", "!!")
        }
    }


}