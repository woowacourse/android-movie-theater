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
import woowacourse.movie.TicketActivity
import woowacourse.movie.movie.dto.BookingMovieDto

class AlarmReceiver : BroadcastReceiver() {

    private lateinit var settingPreference: SettingPreference

    override fun onReceive(context: Context, intent: Intent) {
        settingPreference = SettingPreference(context)
        if (intent.action == "alarm" && settingPreference.setting) {
            createNotificationChannel(context)

            val bookingMovie = intent.getSerializableExtra("booking_movie") as BookingMovieDto
            Log.d("bookingMovie", bookingMovie.toString())

            createNotificationBuilder(context, bookingMovie)
        }
    }

    private fun createNotificationPendingIntent(
        context: Context,
        bookingMovie: BookingMovieDto,
    ): PendingIntent {
        val ticketIntent =
            Intent(context, TicketActivity::class.java).putExtra("booking_movie", bookingMovie)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

        return PendingIntent.getActivity(
            context,
            925,
            ticketIntent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun createNotificationChannel(context: Context) {
        val name = "Notification_Channel"
        val descriptionText = "Test"
        val channel =
            NotificationChannel("Test", name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotificationBuilder(context: Context, bookingMovie: BookingMovieDto) {
        val builder = NotificationCompat.Builder(context, "Test").apply {
            setSmallIcon(R.drawable.ic_launcher_background)
            setContentTitle("예매 알림")
            setContentText("${bookingMovie.movie.title} 30분 후에 상영")
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(createNotificationPendingIntent(context, bookingMovie))
            setAutoCancel(true)
        }

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(925, builder.build())
        }
    }
}
