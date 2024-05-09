package woowacourse.movie.ui.setting.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Calendar

object MovieAlarmManager {
    val REQUEST_CODE = LocalTime.now().hashCode()

    fun setAlarm(
        context: Context,
        movieTitle: String,
        userTicketId: Long,
        screeningStartDateTime: LocalDateTime,
    ) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent =
            Intent(context, MovieAlarmReceiver::class.java).apply {
                putExtra("movie_title_id", movieTitle)
                putExtra("ticket_id", userTicketId)
            }
        val alarmIntent =
            PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE)

        val calendar =
            Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                settingCalendar(screeningStartDateTime)
            }

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            alarmIntent,
        )
    }

    private fun Calendar.settingCalendar(screeningStartDateTime: LocalDateTime) {
        set(Calendar.YEAR, screeningStartDateTime.year)
        set(Calendar.MONTH, screeningStartDateTime.monthValue - 1)
        set(Calendar.DAY_OF_MONTH, screeningStartDateTime.dayOfMonth)
        set(Calendar.HOUR_OF_DAY, screeningStartDateTime.hour)
        set(Calendar.MINUTE, screeningStartDateTime.minute - 30)
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, MovieAlarmReceiver::class.java)
        val alarmIntent =
            PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.cancel(alarmIntent)
    }
}
