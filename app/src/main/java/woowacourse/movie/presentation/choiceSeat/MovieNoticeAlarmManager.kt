package woowacourse.movie.presentation.choiceSeat

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import woowacourse.movie.broadcastreceiver.AlarmReceiver
import woowacourse.movie.presentation.complete.CompleteActivity
import woowacourse.movie.presentation.model.TicketModel
import java.time.LocalDateTime
import java.util.Calendar

class MovieNoticeAlarmManager(val context: Context, val ticketModel: TicketModel) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun getTicketAlarmCalendar(reservationTime: LocalDateTime): Calendar {
        val alarmTime = reservationTime.minusMinutes(30L)
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, alarmTime.year)
            set(Calendar.MONTH, alarmTime.monthValue - 1)
            set(Calendar.DAY_OF_MONTH, alarmTime.dayOfMonth)
            set(Calendar.HOUR_OF_DAY, alarmTime.hour)
            set(Calendar.MINUTE, alarmTime.minute)
        }
    }

    fun setAlarm(reservationTime: LocalDateTime) {
        val pendingIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.putExtra(CompleteActivity.TICKET, ticketModel)
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        }
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            getTicketAlarmCalendar(reservationTime).timeInMillis,
            pendingIntent
        )
    }
}
