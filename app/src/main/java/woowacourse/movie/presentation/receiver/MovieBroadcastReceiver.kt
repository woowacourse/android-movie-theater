package woowacourse.movie.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.MovieApplication
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.presentation.notification.ticket.TicketNotification
import woowacourse.movie.util.getSerializableExtraCompat

class MovieBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val app = context.applicationContext as MovieApplication
        val notificationSetting = app.settingRepository
        if (!notificationSetting.isGranted()) return

        val ticket = intent.getSerializableExtraCompat(EXTRA_TICKET, Ticket::class.java)
        ticket ?: return

        val ticketNotification = TicketNotification(context)
        ticketNotification.sendNotification(ticket)
    }

    companion object {
        fun newIntent(
            context: Context,
            ticket: Ticket,
        ): Intent =
            Intent(context, MovieBroadcastReceiver::class.java).apply {
                putExtra(EXTRA_TICKET, ticket)
            }

        private const val EXTRA_TICKET = "ticket"
    }
}
