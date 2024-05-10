package woowacourse.movie.notification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.R
import woowacourse.movie.purchaseConfirmation.PurchaseConfirmationActivity

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra("notificationId", 0)
        val ticketId = intent.getIntExtra("ticketId", 0)

        val notificationIntent = createNotificationIntent(context, ticketId)
        val pendingIntent = createPendingIntent(context, notificationIntent)
        val notification = buildNotification(context, pendingIntent)
        notifyNotification(context, notificationId, notification)
    }

    private fun createNotificationIntent(context: Context, ticketId: Int): Intent {
        return Intent(context, PurchaseConfirmationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("ticketId", ticketId)
        }
    }

    private fun createPendingIntent(context: Context, intent: Intent): PendingIntent {
        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun buildNotification(
        context: Context,
        pendingIntent: PendingIntent
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, "ticket_confirmation_channel")
            .setSmallIcon(R.drawable.ic_home_check)
            .setContentTitle("영화 알림")
            .setContentText("영화 예매가 완료되었습니다. 자세한 정보를 확인하려면 여기를 탭하세요.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }

    private fun notifyNotification(
        context: Context,
        notificationId: Int,
        builder: NotificationCompat.Builder
    ) {
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(notificationId, builder.build())
    }
}
