package woowacourse.movie.presentation.views.ticketingresult.presenter

import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.views.ticketingresult.contract.TicketingResultContract

class TicketingResultPresenter(
    view: TicketingResultContract.View,
    private val reservation: Reservation,
    private val fromMainScreen: Boolean,
) : TicketingResultContract.Presenter(view) {

    init {
        view.showMovieTitle(reservation.movieTitle)
        view.showTheaterName(reservation.theaterName)
        view.showTicketingDate(reservation.movieDate, reservation.movieTime)
        view.showSeats(reservation.seats)
        view.showTicket(reservation.ticket)
        view.showTicketPrice(reservation.totalPrice)
    }

    override fun onShowMainScreen() {
        view.showMainScreen(reservation, fromMainScreen)
    }
}
