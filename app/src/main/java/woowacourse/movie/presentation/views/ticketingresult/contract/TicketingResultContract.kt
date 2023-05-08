package woowacourse.movie.presentation.views.ticketingresult.contract

import woowacourse.movie.presentation.model.Reservation

interface TicketingResultContract {
    interface View {
        val presenter: Presenter

        fun showMainScreen(reservation: Reservation, fromMainScreen: Boolean)
    }

    abstract class Presenter(protected val view: View) {
        abstract fun onShowMainScreen()
        abstract fun getReservation(): Reservation
    }
}
