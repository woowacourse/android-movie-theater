package woowacourse.movie.feature.setting

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import woowacourse.movie.R
import woowacourse.movie.feature.bookingcomplete.view.BookingCompleteActivity
import woowacourse.movie.feature.model.BookingInfoUiModel

class MyReceiver : BroadcastReceiver() {
    private var bookingInfo: BookingInfoUiModel? = null

    override fun onReceive(
        context: Context,
        intent: Intent?,
    ) {
        bookingInfo = intent?.getParcelableExtra<BookingInfoUiModel>("BOOKING_INFO")
        val title = bookingInfo?.movie?.title

        showNotification(context, title)
    }

    private fun showNotification(
        context: Context,
        title: String?,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notificationIntent =
            Intent(context, BookingCompleteActivity::class.java).apply {
                putExtra("BOOKING_INFO", bookingInfo)
            }
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val builder =
            NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_dino_blue)
                .setContentTitle("예매 알림")
                .setContentText("$title 30분 후에 상영")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)

        val notificationManager =
            ContextCompat.getSystemService(context, NotificationManager::class.java)
        val notificationId = bookingInfo?.uid.hashCode()
        notificationManager?.notify(notificationId, builder.build())
    }

    companion object {
        private const val CHANNEL_ID = "booking_history_channel"
    }
}
