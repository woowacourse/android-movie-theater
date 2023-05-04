package woowacourse.movie.view

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import woowacourse.movie.view.model.ReservationUiModel
import woowacourse.movie.view.seatselection.AlarmReceiver
import java.time.ZoneId

class AlarmController(private val context: Context) {
    init {
        createChannel()
    }

    fun registerAlarms(reservations: List<ReservationUiModel>, minuteInterval: Long) {
        reservations.forEach {
            registerAlarm(it, minuteInterval)
        }
    }

    fun registerAlarm(reservation: ReservationUiModel, minuteInterval: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = getPendingIntent(AlarmReceiver.newIntent(context, reservation))

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            reservation.screeningDateTime.minusMinutes(minuteInterval)
                .atZone(ZoneId.systemDefault())
                .toEpochSecond() * 1000L,
            pendingIntent,
        )
    }

    fun cancelAlarms() {
        val pendingIntent = getPendingIntent(Intent(context, AlarmReceiver::class.java))
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    fun createChannel() {
        val channel = NotificationChannel(AlarmReceiver.CHANNEL_ID, CHANNER_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (!notificationManager.notificationChannels.contains(channel)) notificationManager.createNotificationChannel(channel)
    }

    private fun getPendingIntent(intent: Intent) = PendingIntent.getBroadcast(
        context,
        ALARM_REQUEST_CODE,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
    )

    companion object {
        private const val ALARM_REQUEST_CODE = 100
        private const val CHANNER_NAME = "Reservation Notification"
    }
}
