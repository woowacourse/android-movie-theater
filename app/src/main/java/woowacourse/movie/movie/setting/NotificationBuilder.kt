package woowacourse.movie.movie.setting

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.R
import woowacourse.movie.movie.dto.movie.BookingMovieEntity
import woowacourse.movie.movie.utils.PendingIntentBuilder

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

    fun createNotificationBuilder(bookingMovie: BookingMovieEntity) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.cute_android)
            setContentTitle(NOTIFICATION_TITLE)
            setContentText(NOTIFICATION_TEXT.format(bookingMovie.movie.title))
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(PendingIntentBuilder(context).createNotificationPendingIntent(bookingMovie))
            setAutoCancel(true)
        }
        Log.d("test", "알람 빌더 성공")

        notifyBuilder(context, builder)
        Log.d("test", "빌드 성공")
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
        private const val NOTIFICATION_TEXT = "%s ${AlarmReceiver.ALARM_TIME}분 후에 상영"
    }
}
