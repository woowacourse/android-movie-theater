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

class AlarmController(
    private val context: Context
) {

    init {
        createChannel()
    }

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun registerAlarms(reservations: List<ReservationUiModel>, minuteInterval: Long) {
        reservations.forEach {
            registerAlarm(it, minuteInterval)
        }
    }

    fun registerAlarm(reservation: ReservationUiModel, minuteInterval: Long) {
        val pendingIntent = makeAlarmBroadcastPendingIntent(reservation)

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            reservation.screeningDateTime.minusMinutes(minuteInterval)
                .atZone(ZoneId.systemDefault())
                .toEpochSecond() * 1000L,
            pendingIntent,
        )
    }

    private fun makeAlarmBroadcastPendingIntent(reservation: ReservationUiModel): PendingIntent {
        val alarmReceiverIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.RESERVATION, reservation)
        }
        return PendingIntent.getBroadcast(
            context,
            ALARM_REQUEST_CODE,
            alarmReceiverIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
    }

    fun cancelAlarms() {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ALARM_REQUEST_CODE,
            Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    private fun createChannel() {
        val channel = NotificationChannel(
            AlarmReceiver.CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT,
        )
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    companion object {
        private const val ALARM_REQUEST_CODE = 100
        private const val CHANNEL_NAME = "Reservation Notification"
    }
}
