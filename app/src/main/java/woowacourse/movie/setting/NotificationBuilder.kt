package woowacourse.movie.setting

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import domain.BookingMovie
import woowacourse.movie.R
import woowacourse.movie.mapper.movie.mapToUIModel
import woowacourse.movie.seat.SeatSelectionActivity.Companion.ALARM_TIME
import woowacourse.movie.utils.PendingIntentBuilder

class NotificationBuilder(val context: Context) {

    fun createNotificationChannel() {
        val name = CHANNEL_NAME
        val descriptionText = CHANNEL_TEXT
        val channel =
            NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun createNotificationBuilder(bookingMovie: BookingMovie) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.cute_android)
            setContentTitle(NOTIFICATION_TITLE)
            setContentText(NOTIFICATION_TEXT.format(bookingMovie.title))
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(PendingIntentBuilder(context).createNotificationPendingIntent(bookingMovie.mapToUIModel()))
            setAutoCancel(true)
        }

        notifyBuilder(context, builder)
    }

    private fun notifyBuilder(context: Context, builder: NotificationCompat.Builder) {
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(AlarmReceiver.REQUEST_CODE, builder.build())
        }
    }

    companion object {
        private const val CHANNEL_ID = "alarm_channel"
        private const val CHANNEL_NAME = "영화 예매 알림"
        private const val CHANNEL_TEXT = "영화 예매 30분 전에 알림이 울리도록 하는 기능"
        private const val NOTIFICATION_TITLE = "예매 알림"
        private const val NOTIFICATION_TEXT = "%s ${ALARM_TIME}분 후에 상영"
    }
}
