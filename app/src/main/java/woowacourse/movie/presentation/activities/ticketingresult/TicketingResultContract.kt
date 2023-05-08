package woowacourse.movie.presentation.activities.ticketingresult

import woowacourse.movie.presentation.model.item.Reservation

interface TicketingResultContract {
    interface View {
        val presenter: TicketingResultPresenter

        fun setMovieInformation(reservation: Reservation)
        fun setPaymentPrice(reservation: Reservation)
    }

    interface Presenter {
        fun updateMovieInformation(reservation: Reservation)
        fun updatePaymentPrice(reservation: Reservation)
    }
}
