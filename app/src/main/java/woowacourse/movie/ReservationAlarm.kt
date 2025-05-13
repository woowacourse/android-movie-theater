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
            putExtra(KEY_BOOKING_STATUS, bookingStatus)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            bookingStatus.hashCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val trigger = reservationDateTimeMillis - BEFORE_30_MINS

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            trigger,
            pendingIntent
        )
    }

    companion object {
        private const val KEY_BOOKING_STATUS = "booking_status"
        private const val BEFORE_30_MINS = 1_800_000
    }
}
