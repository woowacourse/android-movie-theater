package woowacourse.movie.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import woowacourse.movie.view.model.ReservationUiModel
import woowacourse.movie.view.seatselection.AlarmReceiver
import java.time.ZoneId

class AlarmController(
    private val context: Context
) {

    fun registerAlarms(reservations: List<ReservationUiModel>, minuteInterval: Long) {
        reservations.forEach {
            registerAlarm(it, minuteInterval)
        }
    }

    fun registerAlarm(reservation: ReservationUiModel, minuteInterval: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = getPendingIntent(reservation)

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            reservation.screeningDateTime.minusMinutes(minuteInterval)
                .atZone(ZoneId.systemDefault())
                .toEpochSecond() * 1000L,
            pendingIntent,
        )
    }

    private fun getPendingIntent(reservation: ReservationUiModel): PendingIntent {
        return Intent(context, AlarmReceiver::class.java).let {
            it.putExtra(AlarmReceiver.RESERVATION, reservation)
            PendingIntent.getBroadcast(
                context,
                ALARM_REQUEST_CODE,
                it,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )
        }
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

    companion object {
        private const val ALARM_REQUEST_CODE = 100
    }
}
