package preview.android.activity.alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout.VERTICAL
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import preview.android.BaseActivity
import preview.android.R
import preview.android.activity.main.MainViewModel
import preview.android.activity.main.fragment.home.HomeMentorDecoration
import preview.android.data.AccountStore
import preview.android.data.AlarmStore
import preview.android.databinding.ActivityAlarmBinding

@AndroidEntryPoint
class AlarmActivity : BaseActivity<ActivityAlarmBinding, MainViewModel>(
    R.layout.activity_alarm
) {

    override val vm: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vm.readAlarmList(AccountStore.nickname.value!!)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.rvAlarm.run {
            setLayoutManager(layoutManager)
            setHasFixedSize(true)
            setItemViewCacheSize(10)
            addItemDecoration(DividerItemDecoration(context, VERTICAL))

            adapter = AlarmAdapter().apply {
                submitList(listOf())
            }
        }

        AlarmStore.alarmObject.observe(this){ list ->
            (binding.rvAlarm.adapter as AlarmAdapter).submitList(list.value.toMutableList())
        }
    }

}