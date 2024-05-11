package woowacourse.movie.ui.setting.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import woowacourse.movie.ui.setting.MovieSettingKey
import java.time.LocalDateTime
import java.time.ZoneId

object MovieAlarmManager {
    private const val ALARM_OFFSET = 30L

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

        if (isReservationAfterCurrentTime(screeningStartDateTime)) {
            val alarmTime =
                screeningStartDateTime
                    .minusMinutes(ALARM_OFFSET)
                    .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

            alarmManager.setAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmTime,
                makePendingIntent(context, userTicketId.toInt(), intent),
            )
        }
    }

    fun cancelAlarm(
        context: Context,
        requestCode: Int,
    ) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, MovieAlarmReceiver::class.java)
        alarmManager.cancel(makePendingIntent(context, requestCode, intent))
    }

    private fun makePendingIntent(
        context: Context,
        requestCode: Int,
        intent: Intent,
    ): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )

    private fun isReservationAfterCurrentTime(screeningStartDateTime: LocalDateTime): Boolean {
        return screeningStartDateTime.isAfter(LocalDateTime.now())
    }
}
