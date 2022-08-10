package preview.android.activity.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.alarm.AlarmActivity
import preview.android.activity.util.*
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

        AccountStore.updateFcmToken(getFCMToken())

        navController =
            (supportFragmentManager.findFragmentById(R.id.fcv_fragment) as NavHostFragment).navController
        binding.bnvMain.setupWithNavController(navController)

        binding.tbMain.setNavigationOnClickListener { view ->
            startActivity(Intent(this, AlarmActivity::class.java))
        }
        binding.tbMain.setOnMenuItemClickListener { item ->
            when (vm.fragmentState.value) {
                MainViewModel.FragmentState.newMentor -> {
                    navController.navigate(R.id.action_newMentorFragment_to_settingFragment)
                }
                MainViewModel.FragmentState.recommendMentor -> {
                    navController.navigate(R.id.action_recommendMentorFragment_to_settingFragment)
                }
                MainViewModel.FragmentState.home -> {
                    navController.navigate(R.id.action_homeFragment_to_settingFragment)
                }
            }
            true
        }
        binding.fab.setOnClickListener {
            if (isFabOpened(binding.fab)) {
                changeFabClose(binding.fab, binding.layoutFabClick, R.drawable.ic_baseline_add)
            } else {
                changeFabOpen(binding.fab, binding.layoutFabClick)
            }
        }

        binding.btnWrite.setOnClickListener {
            if (AccountStore.isMentored.value!!) {
                showDialogFragment(this, WriteDialogFragment())
            } else {
                checkCertifyProgressOn(progressDialog)
            }
            Log.e("write", "!!")
        }
        binding.btnCheckMentor.setOnClickListener {
            showDialogFragment(this, CertifyMentorDialogFragment())
            Log.e("check", "!!")
        }

        vm.fragmentState.observe(this) { state ->
            Log.e("STATE", state.toString())
            changeFabClose(binding.fab, binding.layoutFabClick, R.drawable.ic_baseline_add)
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
                // 멘토 인증 화면으로 연결
            }
        }
    }

    private fun isVerifyMentor(): Boolean {
        // 인증여부 확인
        return true
    }

    fun checkCertifyOkButtonOnClick(view: View) {
        checkCertifyProgressOff(progressDialog)
    }
}