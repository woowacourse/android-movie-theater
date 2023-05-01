package woowacourse.movie.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.R

object NotificationGenerator {

    private var notificationId = 0

    fun createChannel(
        context: Context,
        channelInfo: NotificationChannelInfo
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            channelInfo.id,
            channelInfo.description,
            NotificationManager.IMPORTANCE_HIGH
        )

        notificationManager.createNotificationChannel(channel)
    }

    fun generate(
        context: Context,
        dialogInfo: NotificationDialogInfo,
        intent: Intent
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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
}
