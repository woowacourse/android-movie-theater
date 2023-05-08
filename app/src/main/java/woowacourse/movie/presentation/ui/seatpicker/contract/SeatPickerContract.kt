package woowacourse.movie.presentation.ui.seatpicker.contract

import woowacourse.movie.presentation.base.BaseContract
import woowacourse.movie.presentation.model.PickedSeats
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.model.Seat
import woowacourse.movie.presentation.model.TicketPrice

interface SeatPickerContract {
    interface View : BaseContract.View {
        fun showMovieTitle(title: String)
        fun registerPushBroadcast(reservation: Reservation)
        fun showTicketingResultScreen(reservation: Reservation)
        fun updateReservationBtnEnabled(isEnabled: Boolean)
        fun updateTotalPriceView(ticketPrice: TicketPrice)
        fun initSeatTable(rowSize: Int, colSize: Int)
        fun fetchPickedSeatViews(pickedIndices: List<Int>)
        fun setSeatViewPickState(index: Int, isPicked: Boolean)
        fun showSeatExceedAlertMessage()
        fun showTicketingConfirmDialog()
    }

    abstract class Presenter(view: View) : BaseContract.Presenter<View>(view) {
        abstract fun setState(state: PickedSeats)
        abstract fun getState(): PickedSeats
        abstract fun reserveMovie()
        abstract fun isAllPicked(): Boolean
        abstract fun changeSeatState(seat: Seat)
        abstract fun reserve()
    }
}
