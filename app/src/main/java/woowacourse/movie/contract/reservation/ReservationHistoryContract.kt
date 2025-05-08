package woowacourse.movie.contract.reservation

import woowacourse.movie.domain.ticket.Ticket

interface ReservationHistoryContract {
    interface Presenter {
        fun fetchReservationHistories()

        fun selectReservation(ticket: Ticket)
    }

    interface View {
        fun updateReservationHistories()

        fun showTicket(ticket: Ticket)
    }
}
