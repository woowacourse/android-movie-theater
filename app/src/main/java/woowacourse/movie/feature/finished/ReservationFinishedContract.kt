package woowacourse.movie.feature.finished

import woowacourse.movie.model.ticket.Ticket

interface ReservationFinishedContract {
    interface View {
        fun showReservationHistory(ticket: Ticket)

        fun showErrorSnackBar()

        fun navigateToHome()

        fun notifyScreeningTime(ticket: Ticket)
    }

    interface Presenter {
        fun loadTicket()
    }
}
