package preview.android.data

import androidx.lifecycle.LiveData
import preview.android.activity.util.MutableListLiveData
import preview.android.model.Alarm

object AlarmStore {

    private val _alarmList = MutableListLiveData<Alarm>()
    val alarmList : LiveData<List<Alarm>> get() = _alarmList

    fun addAlarm(alarm : Alarm) {
        val list =_alarmList.value!!.toMutableList()
        list.add(alarm)
        _alarmList.backgroundClear()
        _alarmList.backgroundAddAll(list)
    }
}