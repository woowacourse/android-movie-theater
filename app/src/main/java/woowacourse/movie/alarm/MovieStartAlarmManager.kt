package woowacourse.movie.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// 영화 시작 30분 전에 이벤트 호출
class MovieStartAlarmManager(
    val context: Context,
    val ticket: MovieTicketUiModel,
) {
    private val alarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @SuppressLint("ScheduleExactAlarm")
    fun setAlarm() {
        val receiverIntent = Intent(context, AlarmReceiver::class.java)
        receiverIntent.putExtra("ticket", ticket)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                0,
                receiverIntent,
                PendingIntent.FLAG_IMMUTABLE,
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
        val zonedDateTime = alarmTime.atZone(ZoneId.of("UTC"))
        return zonedDateTime.toInstant().toEpochMilli()
    }
}
