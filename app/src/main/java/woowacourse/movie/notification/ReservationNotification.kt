package woowacourse.movie.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import woowacourse.movie.notification.ReservationNotificationReceiver.Companion.MOVIE_TITLE
import woowacourse.movie.notification.ReservationNotificationReceiver.Companion.TICKET_ID_RECEIVER
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object ReservationNotification {
    const val NOTIFICATION_ID = "notificationId"
    const val REQUEST_ID = "requestId"
    private const val DEFAULT_NOTIFICATION_ID = 0
    private const val ALARM_MINUTE = 0

    fun setNotification(
        context: Context,
        ticketId: Long,
        movieTitle: String,
        screeningDateTime: String,
    ) {
        // Test용 2millisecond 추가한 값
        // val screeningTime = Calendar.getInstance().timeInMillis + 2
        // 실제 영화 상영 시간
        val screeningTime = makeScreeningTime(screeningDateTime)
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
                alarmManager.set(
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

    private fun makeScreeningTime(screeningDateTime: String): Long {
        return SimpleDateFormat(
            "yyyy-MM-dd HH:mm",
            Locale.getDefault(),
        ).parse(screeningDateTime)?.time ?: -1L
    }

    private fun isValidScreeningTime(screeningTime: Long): Boolean {
        val currentDateTime = Calendar.getInstance().timeInMillis
        return screeningTime != -1L && currentDateTime < screeningTime
    }

    private fun makePendingIntent(
        context: Context,
        movieTitle: String,
        ticketId: Long,
    ): PendingIntent {
        val intent = Intent(context, ReservationNotificationReceiver::class.java)
        val bundle = Bundle()
        bundle.putLong(TICKET_ID_RECEIVER, ticketId)
        bundle.putString(MOVIE_TITLE, movieTitle)
        bundle.putInt(REQUEST_ID, getTicketId(sharedPreferences = context.getSharedPreferences(NOTIFICATION_ID, 0)))
        intent.putExtras(bundle)

        return PendingIntent.getBroadcast(
            context,
            getTicketId(sharedPreferences = context.getSharedPreferences(NOTIFICATION_ID, 0)),
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    fun getNextNotificationTicketId(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(NOTIFICATION_ID, MODE_PRIVATE)
        val currentId = getTicketId(sharedPreferences)
        val nextId = currentId + 1
        if (isTicketIdValidate(nextId)) {
            saveTicketId(nextId, sharedPreferences)
        } else {
            resetTicketId(sharedPreferences)
        }
        return nextId
    }

    private fun resetTicketId(sharedPreferences: SharedPreferences) {
        saveTicketId(DEFAULT_NOTIFICATION_ID, sharedPreferences)
    }

    private fun isTicketIdValidate(ticketId: Int): Boolean {
        return ticketId < Int.MAX_VALUE - 1
    }

    fun getTicketId(sharedPreferences: SharedPreferences): Int {
        return sharedPreferences.getInt(NOTIFICATION_ID, DEFAULT_NOTIFICATION_ID)
    }

    private fun saveTicketId(
        nextId: Int,
        sharedPreferences: SharedPreferences,
    ) {
        sharedPreferences.edit().putInt(NOTIFICATION_ID, nextId).apply()
    }
}
