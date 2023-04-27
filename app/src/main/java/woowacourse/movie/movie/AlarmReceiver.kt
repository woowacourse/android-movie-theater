package woowacourse.movie.movie

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.R
import woowacourse.movie.SeatSelectionActivity.Companion.BOOKING_MOVIE_KEY
import woowacourse.movie.TicketActivity
import woowacourse.movie.movie.dto.BookingMovieDto

class AlarmReceiver : BroadcastReceiver() {

    private lateinit var settingPreference: SettingPreference

    override fun onReceive(context: Context, intent: Intent) {
        settingPreference = SettingPreference(context)
        if (intent.action == ALARM_CODE && settingPreference.setting) {
            createNotificationChannel(context)

            val bookingMovie = intent.getSerializableExtra(BOOKING_MOVIE_KEY) as BookingMovieDto
            Log.d(BOOKING_MOVIE_KEY, bookingMovie.toString())

            createNotificationBuilder(context, bookingMovie)
        }
    }

    private fun createNotificationPendingIntent(
        context: Context,
        bookingMovie: BookingMovieDto,
    ): PendingIntent {
        val ticketIntent =
            Intent(context, TicketActivity::class.java).putExtra(BOOKING_MOVIE_KEY, bookingMovie)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

        return PendingIntent.getActivity(
            context,
            REQUEST_CODE,
            ticketIntent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun createNotificationChannel(context: Context) {
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

    private fun createNotificationBuilder(context: Context, bookingMovie: BookingMovieDto) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.cute_android)
            setContentTitle(NOTIFICATION_TITLE)
            setContentText(NOTIFICATION_TEXT.format(bookingMovie.movie.title))
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(createNotificationPendingIntent(context, bookingMovie))
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
            notify(REQUEST_CODE, builder.build())
        }
    }

    companion object {
        const val ALARM_CODE = "alarm"
        const val ALARM_TIME = 30
        private const val CHANNEL_ID = "alarm_channel"
        private const val CHANNEL_NAME = "영화 예매 알림"
        private const val CHANNEL_TEXT = "영화 예매 30분 전에 알림이 울리도록 하는 기능"
        private const val NOTIFICATION_TITLE = "예매 알림"
        private const val NOTIFICATION_TEXT = "%s ${ALARM_TIME}분 후에 상영"
        const val REQUEST_CODE = 925
    }
}
