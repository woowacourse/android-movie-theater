package woowacourse.movie.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import woowacourse.movie.R
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import woowacourse.movie.presentation.view.reservation.result.ReservationResultActivity
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class MovieStartAlarmManager(
    private val context: Context,
    private val ticket: MovieTicketUiModel,
) {
    private val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @SuppressLint("ScheduleExactAlarm")
    fun setAlarm() {
        val receiverIntent = Intent(context, AlarmReceiver::class.java)
        receiverIntent.putExtra(ReservationResultActivity.INTENT_TICKET, ticket)
        receiverIntent.action = context.getString(R.string.notification_action)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                System.currentTimeMillis().toInt(),
                receiverIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
        val alarmTime = dateTimeBefore30min()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmTime,
                pendingIntent,
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                alarmTime,
                pendingIntent,
            )
        }
    }

    private fun dateTimeBefore30min(): Long {
        val date = LocalDate.parse(ticket.screeningDate, DateTimeFormatter.ISO_LOCAL_DATE)
        val time = LocalTime.parse(ticket.startTime, DateTimeFormatter.ofPattern("HH:mm"))
        val startTime = LocalDateTime.of(date, time)
        val alarmTime = startTime.minusMinutes(30)
        val zonedDateTime = alarmTime.atZone(ZoneId.systemDefault())
        return zonedDateTime.toInstant().toEpochMilli()
    }
}
