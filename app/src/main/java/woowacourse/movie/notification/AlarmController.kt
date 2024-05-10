package woowacourse.movie.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import woowacourse.movie.model.Reservation
import woowacourse.movie.utils.dateFormatter
import woowacourse.movie.utils.timeFormatter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class AlarmController(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setNotification(reservation: Reservation) {
        val intent =
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra("reservation", reservation)
            }
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                Notification.NOTIFICATION_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val date = LocalDate.parse(reservation.screeningDate, dateFormatter)
        val time = LocalTime.parse(reservation.screeningTime, timeFormatter)

        calculateDateTime(date, time)?.let {
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                it,
                pendingIntent,
            )
        }
    }

    private fun calculateDateTime(
        reservedDate: LocalDate,
        reservedTime: LocalTime,
    ): Long? {
        val dateTime = LocalDateTime.of(reservedDate, reservedTime)
        val screeningTime = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val alarmTime = screeningTime - ALARM_OFFSET_MINUTES * 60 * 1000
        val currentTime = System.currentTimeMillis()

        return if (alarmTime > currentTime) alarmTime else null
//        return currentTime + 15 * 1000
    }

    companion object {
        const val ALARM_OFFSET_MINUTES = 30
    }
}
