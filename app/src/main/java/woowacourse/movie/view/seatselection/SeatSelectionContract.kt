package woowacourse.movie.view.seatselection

import woowacourse.movie.domain.theater.Grade
import woowacourse.movie.view.model.ReservationUiModel
import woowacourse.movie.view.model.SeatInfoUiModel
import woowacourse.movie.view.model.SeatUiModel

interface SeatSelectionContract {
    interface View {
        fun createSeat(seat: SeatUiModel)
        fun createRow(seatInfo: SeatInfoUiModel)
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
