package woowacourse.movie.presentation.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.data.preference.PreferenceManager
import woowacourse.movie.presentation.common.extension.getParcelableCompat
import woowacourse.movie.presentation.common.model.TicketUiModel
import woowacourse.movie.presentation.home.reservation.result.ReservationResultActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (!PreferenceManager.getInstance(context).isNotificationEnabled) return

        val ticket = intent.getParcelableCompat<TicketUiModel>(AlarmHelper.KEY_TICKET)
        showNotification(context, ticket)
    }

    private fun showNotification(
        context: Context,
        ticket: TicketUiModel,
    ) {
        val pendingIntent = createPendingIntent(context, ticket)
        val notification = buildNotification(context, ticket, pendingIntent)

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(ticket.hashCode(), notification)
    }

    private fun createPendingIntent(
        context: Context,
        ticket: TicketUiModel,
    ): PendingIntent {
        val activityIntent = Intent(context, ReservationResultActivity::class.java)
        activityIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        activityIntent.putExtra(AlarmHelper.KEY_TICKET, ticket)

        return PendingIntent.getActivity(
            context,
            ticket.hashCode(),
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
    }

    private fun buildNotification(
        context: Context,
        ticket: TicketUiModel,
        pendingIntent: PendingIntent,
    ) = NotificationCompat
        .Builder(context, AlarmHelper.CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle(context.getString(R.string.notification_title))
        .setContentText(context.getString(R.string.notification_description).format(ticket.title))
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()
}
