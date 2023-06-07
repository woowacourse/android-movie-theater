package woowacourse.movie.presentation.activities.ticketingresult

import woowacourse.movie.presentation.model.item.Reservation

interface TicketingResultContract {
    interface View {
        val presenter: TicketingResultPresenter

        fun showReservation(reservation: Reservation)
    }

    interface Presenter {
        fun updateMovieInformation(reservation: Reservation)
    }
}
