package woowacourse.movie.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import woowacourse.movie.model.Reservation
import woowacourse.movie.notification.AlarmReceiver.Companion.createIntent
import woowacourse.movie.utils.dateFormatter
import woowacourse.movie.utils.timeFormatter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class AlarmController(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setNotification(reservation: Reservation) {
        val pendingIntent = createAlarmPendingIntent(reservation)
        val dateTime = convertToDateTime(reservation.screeningDate, reservation.screeningTime)

        calculateDateTimeToMilli(dateTime)?.let {
            alarmManager.set(AlarmManager.RTC_WAKEUP, it, pendingIntent)
        }
    }

    private fun createAlarmPendingIntent(reservation: Reservation): PendingIntent {
        val intent = createIntent(context, reservation)

        return PendingIntent.getBroadcast(
            context,
            NotificationManager.NOTIFICATION_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun calculateDateTimeToMilli(reservedDateTime: LocalDateTime): Long? {
        val screeningTime =
            reservedDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val alarmTime = screeningTime - ALARM_OFFSET_MINUTES * 60 * 1000
        val currentTime = System.currentTimeMillis()

        return if (alarmTime > currentTime) alarmTime else null
    }

    private fun convertToDateTime(
        reservedDate: String,
        reservedTime: String,
    ): LocalDateTime {
        val date = LocalDate.parse(reservedDate, dateFormatter)
        val time = LocalTime.parse(reservedTime, timeFormatter)
        return LocalDateTime.of(date, time)
    }

    companion object {
        const val ALARM_OFFSET_MINUTES = 30
    }
}
