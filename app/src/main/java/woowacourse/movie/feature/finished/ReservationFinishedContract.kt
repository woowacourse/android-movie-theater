package woowacourse.movie.feature.finished

import woowacourse.movie.db.ticket.Ticket

interface ReservationFinishedContract {
    interface View {
        fun showReservationHistory(ticket: Ticket)

        fun showErrorSnackBar()

        fun navigateToHome()
    }

    interface Presenter {
        fun loadTicket()
    }
}
