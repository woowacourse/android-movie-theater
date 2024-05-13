package woowacourse.movie.feature.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.db.ticket.TicketEntity.Companion.DEFAULT_TICKET_ID
import woowacourse.movie.feature.finished.ReservationFinishedActivity
import woowacourse.movie.feature.history.ReservationHistoryFragment.Companion.TICKET_ID
import woowacourse.movie.feature.notification.ScreeningAlarm.Companion.SCREENING_NOTIFICATION_TEXT
import woowacourse.movie.feature.notification.ScreeningAlarm.Companion.SCREENING_NOTIFICATION_TITLE

class ScreeningNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val notification = ScreeningNotification(context)

        val ticketId = intent.getLongExtra(TICKET_ID, DEFAULT_TICKET_ID)
        val title = intent.getStringExtra(SCREENING_NOTIFICATION_TITLE)
        val description = intent.getStringExtra(SCREENING_NOTIFICATION_TEXT)

        if (title != null && description != null) {
            val newIntent = Intent(context, ReservationFinishedActivity::class.java).putExtra(TICKET_ID, ticketId)
            notification.deliver(newIntent, title, description)
        }
    }
}
