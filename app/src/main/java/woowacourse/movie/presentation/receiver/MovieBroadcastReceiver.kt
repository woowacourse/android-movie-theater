package woowacourse.movie.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.common.util.getSerializableExtraCompat
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.presentation.notification.ticket.TicketNotification

class MovieBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
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
