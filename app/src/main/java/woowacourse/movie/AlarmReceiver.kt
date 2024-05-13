package woowacourse.movie

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import woowacourse.movie.purchaseconfirmation.PurchaseConfirmationActivity

class AlarmReceiver : BroadcastReceiver() {
    private lateinit var manager: NotificationManager
    private lateinit var builder: NotificationCompat.Builder

    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val reservationId = intent.getLongExtra("reservation_id", -1)
        val movieTitle = intent.getStringExtra("movie_title")

        val startActivityIntent = PurchaseConfirmationActivity.getIntent(context, reservationId)

        val requestCode = reservationId.toInt()
        val pendingIntent = getPendingIntent(context, requestCode, startActivityIntent)

        manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT,
            ),
        )

        builder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.img_movie_poster)

        val notification =
            builder
                .setContentTitle("예매 알림")
                .setContentText("$movieTitle 영화가 30분 후 시작합니다")
                .setContentIntent(pendingIntent)
                .build()

        manager.notify(1, notification)
    }

    private fun getPendingIntent(
        context: Context,
        requestCode: Int,
        intent: Intent,
    ): PendingIntent =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        } else {
            PendingIntent.getActivity(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT,
            )
        }

    companion object {
        const val CHANNEL_ID = "channel"
        const val CHANNEL_NAME = "channel1"

        fun getIntent(
            context: Context,
            reservationId: Long,
            movieTitle: String,
        ): Intent {
            return Intent(context, AlarmReceiver::class.java).apply {
                putExtra("reservation_id", reservationId)
                putExtra("movie_title", movieTitle)
            }
        }
    }
}
