package woowacourse.movie.presentation.views.seatpicker.contract

import woowacourse.movie.presentation.model.PickedSeats
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.Seat
import woowacourse.movie.presentation.model.TicketPrice

interface SeatPickerContract {
    interface View {
        val presenter: Presenter

        fun showMovieTitle(title: String)
        fun registerPushBroadcast(reservation: Reservation)
        fun showTicketingResultScreen(reservation: Reservation)
        fun updateReservationBtnEnabled(isEnabled: Boolean)
        fun updateTotalPriceView(ticketPrice: TicketPrice)
        fun initSeatTable(rowSize: Int, colSize: Int)
        fun fetchPickedSeatViews(pickedIndices: List<Int>)
        fun setSeatViewPickState(index: Int, isPicked: Boolean)
        fun showSeatExceedAlertMessage()
    }

    abstract class Presenter {
        private var view: View? = null

        open fun attach(view: View) {
            this.view = view
        }

        open fun detach() {
            this.view = null
        }

        protected fun requireView(): View =
            view ?: throw IllegalStateException("View is not attached")

        abstract fun setState(state: PickedSeats)
        abstract fun getState(): PickedSeats
        abstract fun reserveMovie()
        abstract fun isAllPicked(): Boolean
        abstract fun onClickSeat(seat: Seat)
    }
}
