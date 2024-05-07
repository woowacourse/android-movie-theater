package woowacourse.movie.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import woowacourse.movie.model.ticket.Ticket
import woowacourse.movie.notification.TicketNotificationReceiver.Companion.MOVIE_TITLE
import woowacourse.movie.view.reservation.ReservationDetailActivity.Companion.TICKET
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
        ticket: Ticket,
        movieTitle: String,
    ) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TicketNotificationReceiver::class.java)
        intent.putExtra(TICKET, ticket)
        intent.putExtra(MOVIE_TITLE, movieTitle)

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                PENDING_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT,
            )

        val dateTime =
            SimpleDateFormat(
                DATE_PATTERN_FORMAT,
                Locale.getDefault(),
            ).parse(
                DATE_PARSE_FORMAT.format(
                    ticket.screeningDateTime.date,
                    ticket.screeningDateTime.time,
                ),
            )
        val calendar =
            Calendar.getInstance().apply {
                if (dateTime != null) {
                    timeInMillis = dateTime.time
                }
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

    @SuppressLint("ServiceCast")
    fun cancelNotification(context: Context) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TicketNotificationReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                PENDING_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
        alarmManager.cancel(pendingIntent)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(NOTIFICATION_ID)
    }
}
