package woowacourse.movie.presentation.notification.ticket

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.presentation.receiver.MovieBroadcastReceiver
import java.time.ZoneId

class TicketAlarm(
    private val context: Context,
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setTicketAlarm(ticket: Ticket) {
        val intent = MovieBroadcastReceiver.newIntent(context, ticket)

        val alarmTimeInMillis =
            ticket.showtime
                .minusMinutes(30)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

        val alarmIntent =
            PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )

        alarmManager.set(AlarmManager.RTC, alarmTimeInMillis, alarmIntent)
    }
}
