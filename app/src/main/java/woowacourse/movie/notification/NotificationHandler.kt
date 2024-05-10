package woowacourse.movie.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import woowacourse.movie.domain.repository.NotificationRepository
import java.time.LocalDateTime
import java.time.ZoneId

class NotificationHandler(private val context: Context) : NotificationRepository {
    override fun registerNotification(
        reservationId: Long,
        movieTitle: String,
        dateTime: LocalDateTime,
    ): Result<Unit> =
        runCatching {
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            val pendingIntent = createPendingIntent(reservationId, movieTitle)
            val alarmTimeMillis = calculateAlarmTime(dateTime)

            if (alarmTimeMillis != null) {
                setAlarm(alarmManager, alarmTimeMillis, pendingIntent)
            }
        }

    private fun createPendingIntent(
        reservationId: Long,
        movieTitle: String,
    ): PendingIntent {
        val intent =
            Intent(context, NotificationReceiver::class.java).apply {
                putExtra(PUT_EXTRA_KEY_RESERVATION_ID, reservationId)
                putExtra(PUT_EXTRA_KEY_MOVIE_TITLE_ID, movieTitle)
            }
        return PendingIntent.getBroadcast(
            context,
            PENDING_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun calculateAlarmTime(dateTime: LocalDateTime): Long? {
        val screeningTime = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val alarmTime = screeningTime - ALARM_OFFSET_MINUTES * 60 * 1000
        val currentTime = System.currentTimeMillis()

        return if (alarmTime > currentTime) alarmTime else null
    }

    private fun setAlarm(
        alarmManager: AlarmManager,
        alarmTimeMillis: Long,
        pendingIntent: PendingIntent,
    ) {
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

    companion object {
        private const val ALARM_OFFSET_MINUTES = 30

        const val NOTIFICATION_ID = 1
        const val PENDING_REQUEST_CODE = 0

        const val PUT_EXTRA_KEY_RESERVATION_ID = "reservationId"
        const val PUT_EXTRA_KEY_MOVIE_TITLE_ID = "movieTitle"
    }
}
