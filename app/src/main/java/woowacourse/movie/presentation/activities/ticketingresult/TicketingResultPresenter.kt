package woowacourse.movie.presentation.activities.ticketingresult

import woowacourse.movie.presentation.model.item.Reservation

class TicketingResultPresenter(
    val view: TicketingResultContract.View,
) : TicketingResultContract.Presenter {

    override fun updateMovieInformation(reservation: Reservation) {
        view.setMovieInformation(reservation)
    }

    override fun updatePaymentPrice(reservation: Reservation) {
        view.setPaymentPrice(reservation)
    }
}
