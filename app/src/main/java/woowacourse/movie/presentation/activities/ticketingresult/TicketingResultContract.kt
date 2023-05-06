package woowacourse.movie.presentation.activities.ticketingresult

import woowacourse.movie.presentation.model.item.Reservation

interface TicketingResultContract {
    interface View {
        val presenter: TicketingResultPresenter

        fun showMovieInformation(reservation: Reservation)
        fun showPaymentPrice(reservation: Reservation)
    }

    interface Presenter {
        fun updateMovieInformation(reservation: Reservation)
        fun updatePaymentPrice(reservation: Reservation)
    }
}
