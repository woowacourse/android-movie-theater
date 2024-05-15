package woowacourse.movie.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import woowacourse.MovieApplication.Companion.database
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

object MovieNotificationAlarmManager {
    private const val ALARM_OFFSET_MINUTES = 30
    private lateinit var movieNotification: MovieNotification

    fun createNotification(
        context: Context,
        ticketId: Long,
    ) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent =
            MovieNotificationReceiver.createIntent(
                context,
                ticketId,
            )
        val pendingIntent = createBroadcastPendingIntent(context, intent)

        val thread =
            Thread {
                movieNotification =
                    database.reservationHistoryDao()
                        .findReservationHistoryById(
                            ticketId,
                        ).toMovieNotification()
            }
        thread.start()
        thread.join()

        val alarmTimeMillis =
            calculateAlarmTime(
                LocalDate.parse(movieNotification.date),
                LocalTime.parse(movieNotification.time),
            )
        sendNotification(alarmTimeMillis, alarmManager, pendingIntent)
    }

    private fun sendNotification(
        alarmTimeMillis: Long?,
        alarmManager: AlarmManager,
        pendingIntent: PendingIntent,
    ) {
        if (alarmTimeMillis != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        alarmTimeMillis,
                        pendingIntent,
                    )
                }
            } else {
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    alarmTimeMillis,
                    pendingIntent,
                )
            }
        }
    }

    private fun createBroadcastPendingIntent(
        context: Context,
        intent: Intent,
    ): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            MovieNotificationReceiver.PENDING_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun calculateAlarmTime(
        date: LocalDate,
        time: LocalTime,
    ): Long? {
        val dateTime = LocalDateTime.of(date, time)
        val screeningTime = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val alarmTime = screeningTime - ALARM_OFFSET_MINUTES * 60 * 1000
        val currentTime = System.currentTimeMillis()

        return if (alarmTime == currentTime) alarmTime else null
    }
}
