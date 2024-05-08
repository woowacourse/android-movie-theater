package woowacourse.movie.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import woowacourse.movie.domain.repository.NotificationRepository
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Notification(private val context: Context) : NotificationRepository {
    override fun createNotification(
        reservationId: Long,
        movieTitle: String,
        dateTime: LocalDateTime,
    ): Result<Unit> =
        runCatching {
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            val intent =
                Intent(context, NotificationReceiver::class.java).apply {
                    putExtra(PUT_EXTRA_KEY_RESERVATION_ID, reservationId)
                    putExtra(PUT_EXTRA_KEY_MOVIE_TITLE_ID, movieTitle)
                }

            val pendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    PENDING_REQUEST_CODE,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE,
                )

            val date = parseScreeningDateTime(dateTime)

            val calendar =
                Calendar.getInstance().apply {
                    timeInMillis = date.time
                    add(Calendar.MINUTE, ALARM_MINUTE)
                }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent,
                    )
                }
            } else {
                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent,
                )
            }
        }

    private fun parseScreeningDateTime(screeningDateTime: LocalDateTime): Date {
        val formatter = DateTimeFormatter.ofPattern(DATE_PATTERN_FORMAT, Locale.getDefault())
        val dateString = screeningDateTime.format(formatter)
        val dateFormat = SimpleDateFormat(DATE_PATTERN_FORMAT, Locale.getDefault())
        return dateFormat.parse(dateString) ?: throw ParseException("날짜 parse 에러 발생", 0)
    }

    companion object {
        private const val DATE_PATTERN_FORMAT = "yyyy-MM-dd HH:mm"

        const val NOTIFICATION_ID = 1
        const val PENDING_REQUEST_CODE = 0
        private const val ALARM_MINUTE = -30

        const val PUT_EXTRA_KEY_RESERVATION_ID = "reservationId"
        const val PUT_EXTRA_KEY_MOVIE_TITLE_ID = "movieTitle"
    }
}
