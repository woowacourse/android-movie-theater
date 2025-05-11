package woowacourse.movie

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import woowacourse.movie.domain.BookingStatus
import woowacourse.movie.moviebooked.MovieBookedActivity
import java.time.ZoneId

class ReservationAlarm(
    private val context: Context,
) {
    private val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun schedule(bookingStatus: BookingStatus) {
        val reservationDateTimeMillis = bookingStatus.bookedTime
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        val intent = Intent(context, ReservationAlarmReceiver::class.java).apply {
            putExtra("booking_status", bookingStatus)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            bookingStatus.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val trigger = reservationDateTimeMillis - 1_800_000

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            trigger,
            pendingIntent
        )
    }
}
