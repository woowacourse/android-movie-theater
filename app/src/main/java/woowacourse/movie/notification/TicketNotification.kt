package woowacourse.movie.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import woowacourse.movie.R
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.notification.TicketNotificationReceiver.Companion.MOVIE_TITLE
import woowacourse.movie.view.reservation.ReservationDetailActivity.Companion.RESERVATION_TICKET_ID
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object TicketNotification {
    const val PENDING_REQUEST_CODE = 0
    private const val NOTIFICATION_ID = "notificationId"
    private const val DEFAULT_NOTIFICATION_ID = 0
    private const val WRONG_SCREENING_TIME = -1L
    private const val MAX_TICKET_NOTIFICATION_ID = Int.MAX_VALUE - 1
    private const val ALARM_MINUTE = -30

    fun setNotification(
        context: Context,
        ticketId: Long,
        movieTitle: String,
        screeningDateTime: ScreeningDateTime,
    ) {
        val screeningTime =
            makeScreeningTime(
                context = context,
                screeningDateTime = screeningDateTime,
            )
        if (!isValidScreeningTime(screeningTime)) return

        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

        val pendingIntent =
            makePendingIntent(
                context = context,
                movieTitle = movieTitle,
                ticketId = ticketId,
            )

        val calendar =
            Calendar.getInstance().apply {
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

    private fun makeScreeningTime(
        context: Context,
        screeningDateTime: ScreeningDateTime,
    ): Long {
        return SimpleDateFormat(
            context.getString(R.string.ticket_date_pattern_format),
            Locale.getDefault(),
        ).parse(
            context.getString(R.string.ticket_date_format).format(
                screeningDateTime.date,
                screeningDateTime.time,
            ),
        )?.time ?: WRONG_SCREENING_TIME
    }

    private fun isValidScreeningTime(screeningTime: Long): Boolean {
        val currentDateTime = Calendar.getInstance().timeInMillis
        return screeningTime != WRONG_SCREENING_TIME && currentDateTime < screeningTime
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

    fun getNextNotificationTicketId(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(NOTIFICATION_ID, MODE_PRIVATE)
        val currentId = getNotificationTicketId(sharedPreferences)
        val nextId = currentId + 1
        if (isNotificationTicketIdValidate(nextId)) {
            saveNotificationTicketId(nextId, sharedPreferences)
        } else {
            resetNotificationTicketId(sharedPreferences)
        }
        return nextId
    }

    private fun resetNotificationTicketId(sharedPreferences: SharedPreferences) {
        saveNotificationTicketId(DEFAULT_NOTIFICATION_ID, sharedPreferences)
    }

    private fun isNotificationTicketIdValidate(ticketId: Int): Boolean {
        return ticketId < MAX_TICKET_NOTIFICATION_ID
    }

    private fun getNotificationTicketId(sharedPreferences: SharedPreferences): Int {
        return sharedPreferences.getInt(NOTIFICATION_ID, DEFAULT_NOTIFICATION_ID)
    }

    private fun saveNotificationTicketId(
        nextId: Int,
        sharedPreferences: SharedPreferences,
    ) {
        sharedPreferences.edit().putInt(NOTIFICATION_ID, nextId).apply()
    }
}
