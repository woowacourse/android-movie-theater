package woowacourse.movie.presenter.history

import woowacourse.movie.model.ticket.ReservationTicket

class ReservationHistoryContract {
    interface View {
        fun navigateToDetail(reservationTicket: ReservationTicket)

        fun showReservationHistory(tickets: List<ReservationTicket>)
    }

    interface Presenter {
        suspend fun loadReservationTickets()

        fun loadReservationTicket(reservationTicket: ReservationTicket)
    }
}
