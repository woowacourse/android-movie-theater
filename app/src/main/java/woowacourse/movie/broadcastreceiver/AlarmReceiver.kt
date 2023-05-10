package woowacourse.movie.broadcastreceiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.R
import woowacourse.movie.presentation.complete.CompleteActivity
import woowacourse.movie.presentation.model.TicketModel
import woowacourse.movie.presentation.util.getParcelableExtraCompat

class AlarmReceiver : BroadcastReceiver() {

    private lateinit var notificationChannel: NotificationChannel
    override fun onReceive(context: Context, intent: Intent) {
        val ticket =
            intent.getParcelableExtraCompat<TicketModel>(CompleteActivity.TICKET)
                ?: throw NoSuchElementException()

        if (!::notificationChannel.isInitialized) {
            createChannel(context)
        }
        BookedTicketNotification.sendNotification(context, ticket)
    }

    private fun createChannel(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(createNotificationChannel(context))
    }

    private fun createNotificationChannel(context: Context): NotificationChannel {
        val channelId = context.getString(R.string.alarm_channel_id)
        val channelName = context.getString(R.string.alarm_channel_name)
        val channelDescription = context.getString(R.string.alarm_channel_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
        }
        return channel
    }
}
