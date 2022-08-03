package preview.android.activity.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import preview.android.R
import preview.android.activity.login.LoginActivity
import preview.android.activity.main.MainActivity
import preview.android.data.AlarmStore
import preview.android.model.Alarm
import java.text.SimpleDateFormat
import java.util.*

class PreviewFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        showNotification(remoteMessage)
        Log.e("onMessageReceived1", remoteMessage.data.toString())
        Log.e("onMessageReceived1-1", remoteMessage.data["title"].toString())
        Log.e("onMessageReceived1-2", remoteMessage.data["body"].toString())
        Log.e("onMessageReceived2", remoteMessage.notification.toString())
        Log.e("onMessageReceived2-1", ""+ remoteMessage.notification?.body.toString())
        Log.e("onMessageReceived2-2", ""+ remoteMessage.notification?.title.toString())

    }

    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)
        Log.e("onMessageSent", p0)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.e("onNewToken", p0)
    }

    private fun showNotification(remoteMessage: RemoteMessage) {
        Log.e("showNotification", "!!")
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        // TODO: Custom Icon, Message etc
        val channelId = "channel"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_baseline_home)
            .setContentTitle(remoteMessage.data["title"].toString())
            .setContentText(remoteMessage.data["body"].toString())
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)



        // AlarmStore.addAlarm(Alarm(title = remoteMessage.data["title"].toString(), content = remoteMessage.data["body"].toString(), time = format.format(curTime)))

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())

    }
}