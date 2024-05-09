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
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val notificationId = intent.getIntExtra("notificationId", 0)
        val message = intent.getStringExtra("message") ?: ""
        val movieTitle = intent.getStringExtra("title") ?: ""
        val cinemaName = intent.getStringExtra("cinemaName") ?: ""
        val ticketPrice = intent.getStringExtra("ticketPrice") ?: ""
        val seatNumbers = intent.getStringArrayExtra("seatNumber") ?: arrayOf()
        val runningTime = intent.getStringExtra("runningTime") ?: ""
        val timeDate = intent.getStringExtra("timeDate") ?: ""

        val notificationIntent =
            Intent(context, PurchaseConfirmationActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("title", movieTitle)
                putExtra("cinemaName", cinemaName)
                putExtra("ticketPrice", ticketPrice)
                putExtra("seatNumber", seatNumbers)
                putExtra("runningTime", runningTime)
                putExtra("timeDate", timeDate)
            }

        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val notificationBuilder =
            NotificationCompat.Builder(context, "ticket_confirmation_channel")
                .setSmallIcon(R.drawable.ic_home_check)
                .setContentTitle("영화 알림")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}
