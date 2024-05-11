package woowacourse.movie.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class MovieBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder

        val channelId = "one-channel"
        val channelName = "My Channel One"
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        manager.createNotificationChannel(channel)

        builder = NotificationCompat.Builder(context, channelId)

        builder.setSmallIcon(android.R.drawable.ic_popup_reminder)
        builder.setContentTitle("예매 알림")
        builder.setContentText("30분 후에 상영")

        manager.notify(1, builder.build())
    }
}
