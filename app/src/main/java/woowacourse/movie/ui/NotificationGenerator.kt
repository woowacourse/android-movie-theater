package woowacourse.movie.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.R

class NotificationGenerator(private val context: Context) {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun createChannel(channelInfo: NotificationChannelInfo) {
        val channel = NotificationChannel(
            channelInfo.id,
            channelInfo.description,
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationManager.createNotificationChannel(channel)
    }

    fun generate(
        dialogInfo: NotificationDialogInfo,
        intent: Intent
    ) {
        val builder =
            NotificationCompat.Builder(context, NotificationChannelInfo.BOOKING_ALARM.name).apply {
                setSmallIcon(R.mipmap.ic_launcher)
                setContentTitle(dialogInfo.title)
                setContentText(dialogInfo.text)
                setContentIntent(PendingIntentGenerator.generateActivityIntent(intent, context))
                setChannelId(NotificationChannelInfo.BOOKING_ALARM.id)
                setAutoCancel(true)
            }
        notificationManager.notify(notificationId++, builder.build())
    }

    companion object {
        private var notificationId = 0
    }
}
