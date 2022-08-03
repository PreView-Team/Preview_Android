package preview.android.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import preview.android.activity.util.MutableListLiveData
import preview.android.model.AlarmObject

object AlarmStore {

    private val _alarmObject = MutableLiveData<AlarmObject>()
    val alarmObject: LiveData<AlarmObject> get() = _alarmObject

    fun updateAlarmList(alarmObject: AlarmObject) {
        _alarmObject.value = alarmObject
    }
}