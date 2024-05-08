package woowacourse.movie.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.notification.TicketNotificationReceiver.Companion.MOVIE_TITLE
import woowacourse.movie.view.reservation.ReservationDetailActivity.Companion.RESERVATION_TICKET_ID
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object TicketNotification {
    const val NOTIFICATION_ID = 1
    const val PENDING_REQUEST_CODE = 0
    private const val DATE_PATTERN_FORMAT = "yyyy-MM-dd HH:mm"
    private const val DATE_PARSE_FORMAT = "%s %s"
    private const val ALARM_MINUTE = -30

    fun setNotification(
        context: Context,
        ticketId: Long,
        movieTitle: String,
        screeningDateTime: ScreeningDateTime,
    ) {

        val screeningTime = makeScreeningTime(screeningDateTime)
        if (!isValidScreeningTime(screeningTime)) return

        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

        val pendingIntent = makePendingIntent(
            context = context,
            movieTitle = movieTitle,
            ticketId = ticketId
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = screeningTime
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

    private fun makeScreeningTime(screeningDateTime: ScreeningDateTime): Long {
        return SimpleDateFormat(
            DATE_PATTERN_FORMAT,
            Locale.getDefault()
        ).parse(
            DATE_PARSE_FORMAT.format(
                screeningDateTime.date,
                screeningDateTime.time,
            ),
        )?.time ?: -1L
    }

    private fun isValidScreeningTime(
        screeningTime: Long
    ): Boolean {
        val currentDateTime = Calendar.getInstance().timeInMillis
        return screeningTime != -1L && currentDateTime < screeningTime
    }

    private fun makePendingIntent(
        context: Context,
        movieTitle: String,
        ticketId: Long,
    ): PendingIntent {
        val intent = Intent(context, TicketNotificationReceiver::class.java)
        intent.putExtra(MOVIE_TITLE, movieTitle)
        intent.putExtra(RESERVATION_TICKET_ID, ticketId)

        return PendingIntent.getBroadcast(
            context,
            PENDING_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }
}
