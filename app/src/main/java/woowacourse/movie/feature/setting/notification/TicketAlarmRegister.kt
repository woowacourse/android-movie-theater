package woowacourse.movie.feature.setting.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import woowacourse.movie.R
import woowacourse.movie.data.movie.MovieRepository
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.util.MovieIntentConstant.KEY_NOTIFICATION_DESCRIPTION
import woowacourse.movie.util.MovieIntentConstant.KEY_NOTIFICATION_TITLE
import woowacourse.movie.util.MovieIntentConstant.KEY_TICKET_ID
import java.time.LocalDateTime
import java.time.ZoneId

class TicketAlarmRegister(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setReservationAlarm(ticket: Ticket) {
        val alarmTimeInMillis =
            LocalDateTime.of(ticket.screeningDate, ticket.screeningTime)
                .minusMinutes(TICKET_ALARM_INTERVAL_MINUTE)
                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val alarmPendingIntent = alarmPendingIntent(ticket)
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTimeInMillis, alarmPendingIntent)
    }

    fun cancelReservationAlarm(ticket: Ticket) {
        val alarmPendingIntent = alarmPendingIntent(ticket)
        alarmManager.cancel(alarmPendingIntent)
    }

    private fun alarmPendingIntent(ticket: Ticket): PendingIntent {
        val notificationTitle = context.resources.getString(R.string.notification_title)
        val notificationText =
            context.resources.getString(
                R.string.notification_description,
                MovieRepository.getMovieById(ticket.movieId).title,
                TICKET_ALARM_INTERVAL_MINUTE,
            )

        val intent =
            Intent(context, TicketAlarmBroadcastReceiver::class.java)
                .putExtra(KEY_TICKET_ID, ticket.id)
                .putExtra(KEY_NOTIFICATION_TITLE, notificationTitle)
                .putExtra(KEY_NOTIFICATION_DESCRIPTION, notificationText)

        return PendingIntent.getBroadcast(
            context,
            ticket.id.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    companion object {
        private const val TICKET_ALARM_INTERVAL_MINUTE = 30L
    }
}
