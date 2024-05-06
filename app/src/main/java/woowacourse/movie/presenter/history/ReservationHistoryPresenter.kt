package woowacourse.movie.presenter.history

import woowacourse.movie.model.ticket.ReservationTicket
import woowacourse.movie.repository.ReservationTicketRepository

class ReservationHistoryPresenter(
    private val view: ReservationHistoryContract.View,
    private val reservationTicketRepository: ReservationTicketRepository,
) : ReservationHistoryContract.Presenter {
    override fun loadReservationTickets() {
        val tickets = reservationTicketRepository.loadReservationTickets()
        view.showReservationHistory(tickets)
    }

    override fun loadReservationTicket(reservationTicket: ReservationTicket) {
        view.navigateToDetail(reservationTicket)
    }
}
