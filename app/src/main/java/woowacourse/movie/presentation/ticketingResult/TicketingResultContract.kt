package woowacourse.movie.presentation.ticketingResult

import woowacourse.movie.model.Reservation

interface TicketingResultContract {
    interface View {
        fun displayTicketInfo(reservation: Reservation)

        fun showToastMessage(message: String?)
    }

    interface Presenter {
        fun loadTicketInfo(movieReservation: Reservation?)
    }
}
