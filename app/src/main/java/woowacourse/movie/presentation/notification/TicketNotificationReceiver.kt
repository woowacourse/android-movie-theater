package woowacourse.movie.presentation.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.data.notification.NotificationPreference
import woowacourse.movie.domain.model.movie.MovieTicket
import woowacourse.movie.presentation.util.getSerializableExtraCompat

class TicketNotificationReceiver(
    private val notificationPreference: NotificationPreference,
) : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (!notificationPreference.isNotificationEnabled()) return
        val ticket = intent.getSerializableExtraCompat(KEY_TICKET, MovieTicket::class.java) ?: return
        val time = intent.getLongExtra(KEY_TIME, 0)
        NotificationHelper(context).showMovieTicketReminder(ticket, time)
    }

    companion object {
        private const val KEY_TICKET = "Ticket"
        private const val KEY_TIME = "Time"

        fun newIntent(
            context: Context,
            ticket: MovieTicket,
            time: Long,
        ): Intent =
            Intent(
                context,
                TicketNotificationReceiver::class.java,
            ).apply {
                putExtra(KEY_TICKET, ticket)
                putExtra(KEY_TIME, time)
            }
    }
}
