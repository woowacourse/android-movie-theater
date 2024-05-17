package woowacourse.movie

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import woowacourse.movie.db.reservationhistory.ReservationHistory
import java.time.LocalDateTime
import java.time.ZoneId

class ReservationHistoryAlarmManager(
    private val context: Context,
) {
    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun confirmReservationAlarm(reservationHistory: ReservationHistory) {
        val alarmTimeInMillis =
            LocalDateTime.of(reservationHistory.screeningDate, reservationHistory.screeningTime)
                .minusMinutes(30L)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

        val alarmPendingIntent = alarmPendingIntent(reservationHistory)
        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTimeInMillis,
            alarmPendingIntent,
        )
    }

    fun cancelReservationAlarm(reservationHistory: ReservationHistory) {
        val alarmPendingIntent = alarmPendingIntent(reservationHistory)
        alarmManager.cancel(alarmPendingIntent)
    }

    private fun alarmPendingIntent(reservationHistory: ReservationHistory): PendingIntent {
        val intent =
            MovieReservationAlarmReceiver.newIntent(
                context,
                reservationHistory.id,
                context.getString(R.string.reservation_alarm_title),
                String.format(
                    context.getString(R.string.reservation_alarm_content)
                        .format(reservationHistory.reservation.screen.movie.title),
                ),
            )

        return PendingIntent.getBroadcast(
            context,
            reservationHistory.id.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }
}
