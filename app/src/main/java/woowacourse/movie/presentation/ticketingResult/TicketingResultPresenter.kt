package woowacourse.movie.presentation.ticketingResult

import woowacourse.movie.model.Reservation

class TicketingResultPresenter(
    private val ticketingResultView: TicketingResultContract.View,
) : TicketingResultContract.Presenter {
    override fun loadTicketInfo(movieReservation: Reservation?) {
        movieReservation?.let { reservation ->
            ticketingResultView.displayTicketInfo(reservation)
        }
    }
}
