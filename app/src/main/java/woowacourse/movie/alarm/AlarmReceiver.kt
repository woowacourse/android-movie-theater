package woowacourse.movie.alarm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.view.extension.getParcelableCompat
import woowacourse.movie.view.reservation.result.ReservationResultActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent?,
    ) {
        val reservationInfo =
            intent?.getParcelableCompat<ReservationInfo>("reservationInfo") ?: return

        val pendingIntent = createPendingIntent(context, reservationInfo)
        val notification = buildNotification(context, pendingIntent, reservationInfo)

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(reservationInfo.hashCode(), notification)
    }

    private fun createPendingIntent(
        context: Context,
        reservationInfo: ReservationInfo,
    ): PendingIntent {
        val activityIntent = ReservationResultActivity.newIntent(context, reservationInfo)
        activityIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        return PendingIntent.getActivity(
            context,
            reservationInfo.hashCode(),
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
    }

    private fun buildNotification(
        context: Context,
        pendingIntent: PendingIntent,
        reservationInfo: ReservationInfo,
        beforeTime: Int = 30,
    ) = NotificationCompat
        .Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground) // 알림에 뜨는 아이콘
        .setContentTitle(context.getString(R.string.notification_title))
        .setContentText(
            context
                .getString(R.string.notification_description)
                .format(reservationInfo.title, beforeTime),
        ).setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()

    companion object {
        const val CHANNEL_ID = "reservation_alarm_channel"
    }
}
