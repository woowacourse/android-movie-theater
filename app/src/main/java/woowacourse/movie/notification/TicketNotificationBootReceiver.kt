package woowacourse.movie.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.repository.ReservationTicketRepository
import woowacourse.movie.repository.ReservationTicketRepositoryImpl

class TicketNotificationBootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (isBootingState(intent)) {
            context?.let { setNotificationTickets(it, ReservationTicketRepositoryImpl(it)) }
        }
    }

    private fun isBootingState(intent: Intent?): Boolean {
        return intent?.action == Intent.ACTION_BOOT_COMPLETED
    }

    private fun setNotificationTickets(
        context: Context,
        repository: ReservationTicketRepository,
    ) {
        Thread {
            val tickets = repository.loadReservationTickets()
            tickets.forEach { reservationTicket ->
                TicketNotification.setNotification(
                    context = context,
                    ticketId = reservationTicket.ticketId,
                    movieTitle = reservationTicket.movieTitle,
                    screeningDateTime = reservationTicket.screeningDateTime,
                )
            }
        }.start()
    }
}
