package woowacourse.movie.presentation.view.navigation

import android.util.Log
import woowacourse.movie.repository.db.ReservationTicketDao
import woowacourse.movie.repository.db.toTicketUiModel

class ReservationListPresenterImpl(
    private val ticketDao: ReservationTicketDao,
    private val view: ReservationListContract.View
) :
    ReservationListContract.Presenter {

    override fun loadReservationTickets() {
        Thread {
            val tickets = ticketDao.findAllReservation()

            val movieTickets = tickets.map { ticketEntity ->
                ticketEntity.toTicketUiModel()
            }
            view.showReservationTickets(movieTickets)
        }.start()
    }
}

