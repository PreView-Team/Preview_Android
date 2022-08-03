package preview.android.model

import java.io.Serializable

data class AlarmObject(
    val value : List<Alarm> = listOf()
) : Serializable