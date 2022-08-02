package preview.android.repository

import android.util.Log
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import preview.android.BuildConfig
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import kotlin.coroutines.resume

class AlarmRepository {

    suspend fun sendNotice(token: String, myNickname : String): Boolean =
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
}