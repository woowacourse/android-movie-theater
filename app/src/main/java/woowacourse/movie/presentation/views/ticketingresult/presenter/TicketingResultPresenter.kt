package woowacourse.movie.presentation.views.ticketingresult.presenter

import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.views.ticketingresult.contract.TicketingResultContract

class TicketingResultPresenter(
    private val reservation: Reservation,
    private val fromMainScreen: Boolean,
) : TicketingResultContract.Presenter() {

    override fun onShowMainScreen() {
        if (!fromMainScreen) requireView().showMainScreen(reservation)
        requireView().close()
    }

    override fun getReservation(): Reservation = reservation.copy()
}
