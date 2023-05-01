package woowacourse.movie.ui.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.ui.ticket.MovieTicketActivity

class NotificationCreator(
    private val context: Context,
) {
    private val notificationManager: NotificationManager by lazy {
        context.getSystemService(
            Context.NOTIFICATION_SERVICE,
        ) as NotificationManager
    }

    fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            ALARM_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH,
        )
        notificationManager.createNotificationChannel(
            notificationChannel,
        )
    }

    fun makeNotification(movie: MovieTicketModel) {
        val contentPendingIntent = setContentIntent(context, movie)
        val builder = setBuilder(context, movie, contentPendingIntent)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun setContentIntent(
        context: Context,
        movie: MovieTicketModel,
    ): PendingIntent {
        val contentIntent = MovieTicketActivity.getIntent(movie, context)

        return PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun setBuilder(
        context: Context,
        movie: MovieTicketModel,
        contentPendingIntent: PendingIntent?,
    ) = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_launcher_round)
        .setContentTitle(context.getString(R.string.notification_title))
        .setContentText(context.getString(R.string.notification_text, movie.title))
        .setContentIntent(contentPendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    companion object {
        private const val ALARM_CHANNEL_NAME = "reservation_alarm_channel"
        private const val CHANNEL_ID = "channel_id"
        const val NOTIFICATION_ID = 0
    }
}
