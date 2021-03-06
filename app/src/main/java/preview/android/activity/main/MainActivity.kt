package preview.android.activity.main

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
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
import preview.android.data.AccountStore
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
            showDialogFragment(this, CertifyMentorDialogFragment())
            Log.e("check", "!!")
        }

        vm.fragmentState.observe(this) { state ->
            Log.e("STATE", state.toString())
            changeFabClose(binding.fab, binding.layoutFabClick)
            when (state) {
                MainViewModel.FragmentState.newMentor -> {
                    binding.fab.visibility = View.INVISIBLE
                }
                MainViewModel.FragmentState.recommendMentor -> {
                    binding.fab.visibility = View.INVISIBLE
                }
                MainViewModel.FragmentState.home -> {
                    binding.fab.visibility = View.VISIBLE
                }
                MainViewModel.FragmentState.community -> {
                    binding.fab.visibility = View.VISIBLE
                    binding.fab.setImageResource(R.drawable.ic_baseline_write)
                }
                MainViewModel.FragmentState.setting -> {
                    binding.fab.visibility = View.INVISIBLE
                }
            }

        }

        vm.writing.observe(this) { writing ->
            if (isVerifyMentor()) {
                vm.sendWriting(AccountStore.token.value!!, writing)
            } else {
                // ?????? ?????? ???????????? ??????
            }
        }
    }

    private fun isVerifyMentor(): Boolean {
        // ???????????? ??????
        return true
    }
}