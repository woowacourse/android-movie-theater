package woowacourse.movie.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import woowacourse.movie.view.model.ReservationUiModel
import woowacourse.movie.view.seatselection.AlarmReceiver
import java.time.LocalDateTime
import java.time.ZoneId

class AlarmController(
    val context: Context
) {

    fun registerAlarms(reservations: List<ReservationUiModel>) {
        reservations.forEach {
            registerAlarm(it)
        }
    }

    fun registerAlarm(reservation: ReservationUiModel) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = Intent(context, AlarmReceiver::class.java).let {
            it.putExtra(AlarmReceiver.REQUEST_CODE, AlarmReceiver.ALARM_REQUEST_CODE)
            it.putExtra(AlarmReceiver.RESERVATION, reservation)
            PendingIntent.getBroadcast(
                context,
                AlarmReceiver.ALARM_REQUEST_CODE,
                it,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )
        }

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            LocalDateTime.now().plusSeconds(10).atZone(ZoneId.systemDefault())
                .toEpochSecond() * 1000L,
            pendingIntent,
        )
    }

    fun cancelAlarms() {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            AlarmReceiver.ALARM_REQUEST_CODE,
            Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}
