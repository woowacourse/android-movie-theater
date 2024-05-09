package woowacourse.movie.ui.setting.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import woowacourse.movie.ui.setting.MovieSettingKey
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Calendar

object MovieAlarmManager {
    val REQUEST_CODE = LocalTime.now().hashCode()
    private const val MONTH_OFFSET = 1
    private const val ALARM_OFFSET = 30

    fun setAlarm(
        context: Context,
        movieTitle: String,
        userTicketId: Long,
        screeningStartDateTime: LocalDateTime,
    ) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent =
            Intent(context, MovieAlarmReceiver::class.java).apply {
                putExtra(MovieSettingKey.MOVIE_TITLE_ID, movieTitle)
                putExtra(MovieSettingKey.TICKET_ID, userTicketId)
            }

        val calendar =
            Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                settingCalendar(screeningStartDateTime)
            }

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            makePendingIntent(context, intent),
        )
    }

    private fun Calendar.settingCalendar(screeningStartDateTime: LocalDateTime) {
        set(Calendar.YEAR, screeningStartDateTime.year)
        set(Calendar.MONTH, screeningStartDateTime.monthValue - MONTH_OFFSET)
        set(Calendar.DAY_OF_MONTH, screeningStartDateTime.dayOfMonth)
        set(Calendar.HOUR_OF_DAY, screeningStartDateTime.hour)
        set(Calendar.MINUTE, screeningStartDateTime.minute - ALARM_OFFSET)
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MovieAlarmReceiver::class.java)
        alarmManager.cancel(makePendingIntent(context, intent))
    }

    private fun makePendingIntent(
        context: Context,
        intent: Intent,
    ): PendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE)
}
