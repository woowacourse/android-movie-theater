package woowacourse.movie.presentation.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.presentation.common.extension.getParcelableCompat
import woowacourse.movie.presentation.common.model.TicketUiModel
import woowacourse.movie.presentation.home.reservation.result.ReservationResultActivity

class RemindNotificationSender : NotificationSender {
    override fun send(
        context: Context,
        intent: Intent,
    ) {
        val ticket = intent.getParcelableCompat<TicketUiModel>(AlarmHelper.KEY_TICKET)
        val beforeTime = intent.getIntExtra(AlarmHelper.KEY_TIME_BEFORE_MINUTES, 0)

        val pendingIntent = ReservationResultActivity.newPendingIntent(context, ticket)
        val notification = buildNotification(context, ticket, beforeTime, pendingIntent)

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(ticket.hashCode(), notification)
    }

    private fun buildNotification(
        context: Context,
        ticket: TicketUiModel,
        beforeTime: Int,
        pendingIntent: PendingIntent,
    ) = NotificationCompat
        .Builder(context, AlarmHelper.CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle(context.getString(R.string.notification_title))
        .setContentText(
            context.getString(R.string.notification_description).format(ticket.title, beforeTime),
        ).setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()
}
