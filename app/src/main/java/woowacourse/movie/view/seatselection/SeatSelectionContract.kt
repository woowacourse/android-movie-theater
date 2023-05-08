package woowacourse.movie.view.seatselection

import woowacourse.movie.domain.theater.Grade
import woowacourse.movie.view.model.ReservationUiModel
import woowacourse.movie.view.model.SeatInfoUiModel

interface SeatSelectionContract {
    interface View {
        var presenter: Presenter

        fun createSeats(seatInfo: SeatInfoUiModel)
        fun onSeatSelectedByIndex(index: Int, isClickableButton: Boolean)
        fun onSeatDeselectedByIndex(index: Int)
        fun showSeatMaxSelectionToast()
        fun showWrongInputToast()
        fun setPrice(price: Int)
        fun onReserveClick(model: ReservationUiModel)
    }

    interface Presenter {
        fun updateSeat(row: Int, col: Int)
        fun reserve()
        fun fetchSeatsData(colorOfGrade: Map<Grade, Int>)
    }
}
