package woowacourse.movie.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.app.AlarmManagerCompat
import woowacourse.movie.model.Cinema

class NotificationChannelManager(private val context: Context) {

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            channel.description = CHANNEL_DESCRIPTION
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleMovieStartNotification(
        movieStartTime: Long,
        cinema: Cinema,
        ticketId: Int,
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (AlarmManagerCompat.canScheduleExactAlarms(alarmManager)) {
            val alarmTime = movieStartTime - 30 * 60 * 1000
            val intent =
                Intent(context, NotificationReceiver::class.java).apply {
                    putExtra("notificationId", 1001)
                    putExtra("ticketId", ticketId)
                    putExtra("message", "${cinema.theater.movie.title} 영화 시작 30분 전입니다!")
                }
            val pendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    1001,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
                )
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmTime,
                pendingIntent,
            )
        } else {
            context.startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
        }
    }

    companion object {
        private const val CHANNEL_ID = "ticket_confirmation_channel"
        private const val CHANNEL_NAME = "Ticket Confirmation"
        private const val CHANNEL_DESCRIPTION = "Notifications for ticket confirmations"
    }
}
