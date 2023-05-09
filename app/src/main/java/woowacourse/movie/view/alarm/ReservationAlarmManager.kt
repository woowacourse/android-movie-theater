package woowacourse.movie.view.alarm

import android.app.AlarmManager
import android.content.Context
import woowacourse.movie.view.model.ReservationUiModel
import java.time.ZoneId

class ReservationAlarmManager(
    val context: Context
) {

    private val alarmController = AlarmController(context)

    fun registerAlarms(reservations: List<ReservationUiModel>, timeInterval: Long) {
        reservations.forEach { reservation ->
            registerAlarm(reservation, timeInterval)
        }
    }

    fun registerAlarm(reservation: ReservationUiModel, timeInterval: Long) {
        val pendingIntent = AlarmReceiver.createPendingIntent(context, reservation)
        val triggerTime: Long = reservation.screeningDateTime.minusMinutes(timeInterval)
            .atZone(ZoneId.systemDefault())
            .toEpochSecond() * 1000L
        alarmController.registerAlarm(pendingIntent, triggerTime)
    }

    fun cancelAlarms() {
        val pendingIntent = AlarmReceiver.broadcastPendingIntent(context)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}
