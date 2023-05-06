package woowacourse.movie.presentation.views.ticketingresult.contract

import woowacourse.movie.presentation.model.Reservation

interface TicketingResultContract {
    interface View {
        val presenter: Presenter

        fun showMainScreen(reservation: Reservation)
        fun close()
    }

    abstract class Presenter {
        private var view: View? = null

        open fun attach(view: View) {
            this.view = view
        }

        fun detach() {
            view = null
        }

        fun requireView(): View =
            view ?: throw IllegalStateException("View is not attached")

        abstract fun onShowMainScreen()
        abstract fun getReservation(): Reservation
    }
}
