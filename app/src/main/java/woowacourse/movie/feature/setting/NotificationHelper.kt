package woowacourse.movie.feature.setting

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import woowacourse.movie.R
import woowacourse.movie.feature.bookingcomplete.view.BookingCompleteActivity
import woowacourse.movie.feature.model.BookingInfoUiModel

object NotificationHelper {
    fun showNotification(
        context: Context,
        bookingInfo: BookingInfoUiModel,
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
                .Builder(context, "booking_history_channel")
                .setSmallIcon(R.drawable.ic_dino_blue)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(
                    context.getString(
                        R.string.notification_description,
                        bookingInfo.movie.title,
                    ),
                ).setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)

        val notificationManager =
            ContextCompat.getSystemService(context, NotificationManager::class.java)
        val notificationId = bookingInfo.uid.hashCode()
        notificationManager?.notify(notificationId, builder.build())
    }
}
