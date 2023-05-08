package woowacourse.movie.presentation.views.ticketingresult.presenter

import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.views.ticketingresult.contract.TicketingResultContract

class TicketingResultPresenter(
    view: TicketingResultContract.View,
    private val reservation: Reservation,
    private val fromMainScreen: Boolean,
) : TicketingResultContract.Presenter(view) {

    override fun onShowMainScreen() {
        view.showMainScreen(reservation, fromMainScreen)
    }

    override fun getReservation(): Reservation = reservation.copy()
}
