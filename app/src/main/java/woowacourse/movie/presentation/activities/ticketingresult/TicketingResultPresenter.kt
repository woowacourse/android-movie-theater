package woowacourse.movie.presentation.activities.ticketingresult

import woowacourse.movie.presentation.model.item.Reservation

class TicketingResultPresenter(
    val view: TicketingResultContract.View,
) : TicketingResultContract.Presenter {

    override fun updateMovieInformation(reservation: Reservation) {
        view.showMovieInformation(reservation)
    }

    override fun updatePaymentPrice(reservation: Reservation) {
        view.showPaymentPrice(reservation)
    }
}
