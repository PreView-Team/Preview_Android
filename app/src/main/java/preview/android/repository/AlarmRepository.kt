package preview.android.repository

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import preview.android.BuildConfig
import preview.android.model.Alarm
import preview.android.model.AlarmObject
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import kotlin.coroutines.resume

class AlarmRepository {
    val db = Firebase.firestore

    suspend fun sendNotice(token: String, myNickname: String): Boolean =
        suspendCancellableCoroutine { continuation ->
            try {
                var notificationObject = JSONObject()
                var root = JSONObject()
                var fcmServer = BuildConfig.FCM_KEY

                notificationObject.put("body", "지금 확인하기")
                notificationObject.put("title", "${myNickname}님이 메시지를 보냈습니다")
                root.put("data", notificationObject)
                root.put("to", token) // 토큰값을 상대방꺼로

                var url = URL("https://fcm.googleapis.com/fcm/send")
                var conn = url.openConnection() as HttpURLConnection

                conn.requestMethod = "POST"
                conn.doOutput = true
                conn.doInput = true
                conn.addRequestProperty("Authorization", "key=" + fcmServer)
                conn.setRequestProperty("Accept", "application/json")
                conn.setRequestProperty("Content-type", "application/json")

                var os = conn.outputStream
                os.write(root.toString().toByteArray(StandardCharsets.UTF_8))
                os.flush()
                conn.responseCode
                continuation.resume(true)
            } catch (exception: Exception) {
                Log.e("sendNotice exception", exception.toString())
                continuation.resume(false)
            }
        }

    suspend fun readAlarmList(myNickname: String): Any =
        suspendCancellableCoroutine { continuation ->
            db.collection(myNickname).get().addOnSuccessListener { result ->
                continuation.resume(result)
            }.addOnFailureListener { error ->
                continuation.resume(error)
            }
        }

    suspend fun createAlarmList(
        nickname: String,
        alarmlist: AlarmObject
    ): Boolean = suspendCancellableCoroutine { continutaion ->
        db.collection(nickname).document(nickname).set(alarmlist).addOnSuccessListener {
            continutaion.resume(true)
        }.addOnFailureListener {
            Log.e("addAlarmList fail", it.toString())
            continutaion.resume(false)
        }
    }

    // 대상의 닉네임으로 추가해야함
    suspend fun addAlarm(nickname: String, alarmlist: AlarmObject) : Boolean = suspendCancellableCoroutine { continuation ->
        db.collection(nickname).document(nickname).update("value", alarmlist.value).addOnSuccessListener {
            continuation.resume(true)
        }.addOnFailureListener {
            continuation.resume(false)
        }
    }
}