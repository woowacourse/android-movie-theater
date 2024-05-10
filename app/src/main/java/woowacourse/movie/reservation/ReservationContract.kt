package woowacourse.movie.reservation

import woowacourse.movie.database.Ticket

interface ReservationContract {
    interface View {
        fun showReservations(reservations: List<Ticket>)

        fun showError(message: String)

        fun navigateToTicketDetail(ticket: Ticket)
    }

    interface Presenter {
        fun loadData()

        fun navigateDetailView(ticket: Ticket)
    }
}
