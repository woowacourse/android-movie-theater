package woowacourse.movie.presentation.choiceSeat

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import woowacourse.movie.broadcastreceiver.AlarmReceiver
import woowacourse.movie.presentation.complete.CompleteActivity
import woowacourse.movie.presentation.model.TicketModel
import java.time.Duration
import java.time.LocalDateTime

class MovieNoticeAlarmManager(private val context: Context, private val ticketModel: TicketModel) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun getAlarmTime(reservationTime: LocalDateTime): LocalDateTime =
        reservationTime.minusMinutes(30L)

    fun setAlarm(reservationTime: LocalDateTime) {
        val pendingIntent = getPendingIntent()
        val alarmTime = convertReservationTimeToMillis(reservationTime)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTime,
            pendingIntent
        )
    }

    private fun convertReservationTimeToMillis(reservationTime: LocalDateTime): Long {
        val alarmTimeDuration = Duration.between(LocalDateTime.now(), getAlarmTime(reservationTime))
        return (System.currentTimeMillis() + alarmTimeDuration.toMillis())
    }

    private fun getPendingIntent(): PendingIntent? {
        val pendingIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.putExtra(CompleteActivity.TICKET, ticketModel)
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }
        return pendingIntent
    }
}
