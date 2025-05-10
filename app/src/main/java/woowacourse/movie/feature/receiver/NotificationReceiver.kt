package woowacourse.movie.feature.receiver

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import woowacourse.movie.MovieApplication
import woowacourse.movie.R
import woowacourse.movie.domain.repository.SettingRepository
import woowacourse.movie.feature.bookingcomplete.view.BookingCompleteActivity
import woowacourse.movie.feature.model.BookingInfoUiModel
import woowacourse.movie.util.getParcelableExtraCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        val settingRepository: SettingRepository = (context?.applicationContext as MovieApplication).settingRepository

        when {
            intent?.action == null -> return

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED -> return

            settingRepository.fetchNotificationSetting() == false -> return
        }

        val bookingInfo = intent.getParcelableExtraCompat<BookingInfoUiModel>(BOOKING_INFO_KEY) ?: BookingInfoUiModel()
        val notification = createNotification(context, bookingInfo)

        val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java)
        notificationManager?.notify(bookingInfo.hashCode(), notification)
    }

    private fun createNotification(
        context: Context,
        bookingInfo: BookingInfoUiModel,
    ): Notification {
        val activityIntent = BookingCompleteActivity.newIntent(context, bookingInfo)

        val pendingIntent =
            PendingIntent.getActivity(
                context,
                bookingInfo.id?.toInt() ?: 0,
                activityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        return NotificationCompat
            .Builder(context, MOVIE_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_dino_blue)
            .setContentTitle(context.getString(R.string.booking_history_notification_title))
            .setContentText(context.getString(R.string.booking_history_notification_description, bookingInfo.movie.title))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

    companion object {
        const val MOVIE_NOTIFICATION_CHANNEL_ID = "MOVIE_NOTIFICATION_CHANNEL"
        private const val BOOKING_INFO_KEY = "BOOKING_INFO"
        private const val NOTIFICATION_ACTION = "NOTIFICATION"

        fun newIntent(
            context: Context,
            bookingInfo: BookingInfoUiModel,
        ): Intent =
            Intent(context, NotificationReceiver::class.java).apply {
                putExtra(BOOKING_INFO_KEY, bookingInfo)
                action = NOTIFICATION_ACTION
            }
    }
}
