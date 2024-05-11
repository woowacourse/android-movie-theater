package woowacourse.movie

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import woowacourse.movie.presentation.purchaseConfirmation.PurchaseConfirmationActivity

class MovieBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == RESERVATION_NOTIFICATION_ACTION) {
            val movieTitle = intent.getStringExtra("movieTitle") ?: "영화 제목"
            val reservationId =
                intent.getLongExtra(PurchaseConfirmationActivity.EXTRA_RESERVATION_ID, -1)

            if((context.applicationContext as MovieReservationApp).notificationDatastore.canNotification)
                sendNotification(context, movieTitle, reservationId)
        }
    }

    private fun sendNotification(
        context: Context,
        movieTitle: String,
        reservationId: Long
    ) {
        createNotificationChannel(context)
        val pendingIntent = createPendingIntent(context, reservationId)

        val notificationBuilder = NotificationCompat.Builder(context, RESERVATION_NOTIFICATION_ID)
            .setContentTitle("예매 알림")
            .setContentText("$movieTitle 30분 후에 상영")
            .setSmallIcon(R.drawable.ic_notifications_active_24)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notificationManager =
            ContextCompat.getSystemService(context, NotificationManager::class.java)
        notificationManager?.notify(RESERVATION_REQUEST_CODE, notificationBuilder.build())
    }

    private fun createNotificationChannel(context: Context) {
        val channel =
            NotificationChannel(
                RESERVATION_NOTIFICATION_ID,
                "예매 알림",
                NotificationManager.IMPORTANCE_DEFAULT
            )
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }

    private fun createPendingIntent(context: Context, reservationId: Long): PendingIntent {
        val newIntent = PurchaseConfirmationActivity.newIntent(context, reservationId)
        return PendingIntent.getActivity(
            context,
            RESERVATION_REQUEST_CODE,
            newIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        const val RESERVATION_NOTIFICATION_ACTION = "action.pangtae.odoong.hardi"
        const val RESERVATION_REQUEST_CODE = 1
        const val RESERVATION_NOTIFICATION_ID = "pangtae_oddong_hardi"
    }
}