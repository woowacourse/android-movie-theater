package woowacourse.movie.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import woowacourse.movie.presentation.view.reservation.result.ReservationResultActivity

class MovieStartNotificationManager(
    val context: Context,
    val intent: Intent,
) {
    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private lateinit var notificationBuilder: NotificationCompat.Builder

    fun startNotification() {
        val ticket = intent.getParcelableExtra<MovieTicketUiModel>(ReservationResultActivity.INTENT_TICKET)!!

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = context.getString(R.string.notification_channel_id)
            val name = context.getString(R.string.notification_channel_name)
            val descriptionText = context.getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH

            val notificationChannel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }

            notificationManager.createNotificationChannel(notificationChannel)

            notificationBuilder = NotificationCompat.Builder(context, channelId)
        } else {
            notificationBuilder = NotificationCompat.Builder(context)
        }

        notificationBuilder.setSmallIcon(R.drawable.ic_home)
            .setContentTitle(context.getString(R.string.notification_content_title))
            .setContentText(context.getString(R.string.notification_content_text))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)

        pendingIntent(notificationBuilder, ticket)
        notificationManager.notify(ticket.ticketId.toInt(), notificationBuilder.build())
    }

    private fun pendingIntent(
        builder: NotificationCompat.Builder,
        movieTicketUiModel: MovieTicketUiModel,
    ) {
        val actionIntent = Intent(context, ReservationResultActivity::class.java)
        actionIntent.putExtra(ReservationResultActivity.INTENT_TICKET, movieTicketUiModel)
        val actionPending =
            PendingIntent.getActivity(
                context,
                System.currentTimeMillis().toInt(),
                actionIntent,
                PendingIntent.FLAG_IMMUTABLE,
            )
        builder.setContentIntent(actionPending)
    }
}
