package woowacourse.movie.broadcastreceiver

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.R
import woowacourse.movie.model.data.storage.MovieStorage
import woowacourse.movie.presentation.complete.CompleteActivity
import woowacourse.movie.presentation.model.TicketModel
import woowacourse.movie.presentation.util.getParcelableExtraCompat

class AlarmReceiver(private val movieStorage: MovieStorage) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val ticket =
            intent.getParcelableExtraCompat<TicketModel>(CompleteActivity.TICKET)
                ?: throw NoSuchElementException()

        val channel = setNotificationChannel(context)
        initNotificationManager(context, channel)

        val pendingIntent = initPendingIntent(context, ticket)
        val builder = initNotificationBuilder(context, ticket, pendingIntent)
        lunchNotification(context, builder)
    }

    private fun setNotificationChannel(context: Context): NotificationChannel {
        val channelId = context.getString(R.string.alarm_channel_id)
        val channelName = context.getString(R.string.alarm_channel_name)
        val channelDescription = context.getString(R.string.alarm_channel_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
        }
        return channel
    }

    private fun initNotificationManager(
        context: Context,
        channel: NotificationChannel
    ) {
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun initPendingIntent(
        context: Context,
        ticket: TicketModel
    ): PendingIntent? {
        val intent = CompleteActivity.getIntent(context, ticket)

        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun initNotificationBuilder(
        context: Context,
        ticket: TicketModel,
        pendingIntent: PendingIntent?
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, context.getString(R.string.alarm_channel_id))
            .setSmallIcon(R.drawable.ic_home)
            .setContentTitle(context.getString(R.string.booked_ticket_notification_title))
            .setContentText(
                context.getString(
                    R.string.booked_ticket_notification_content,
                    movieStorage.getMovieById(ticket.movieId).title
                )
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }

    private fun lunchNotification(
        context: Context,
        builder: NotificationCompat.Builder
    ) {
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) return
            notify(1, builder.build())
        }
    }
}
