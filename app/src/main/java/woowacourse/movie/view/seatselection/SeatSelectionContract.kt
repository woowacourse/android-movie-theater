package woowacourse.movie.view.seatselection

import woowacourse.movie.domain.system.PriceSystem
import woowacourse.movie.domain.system.SeatSelectSystem
import woowacourse.movie.domain.theater.Grade
import woowacourse.movie.domain.theater.Theater
import woowacourse.movie.view.model.ReservationUiModel
import woowacourse.movie.view.model.SeatInfoUiModel

interface SeatSelectionContract {
    interface View {
        var presenter: Presenter
        fun setSelectionSeat(index: Int, isClickableButton: Boolean)
        fun setDeselectionSeat(index: Int)
        fun maxSelectionToast()
        fun wrongInputToast()
        fun setPrice(price: String)
        fun showSubmitDialog(reservation: ReservationUiModel)
    }

    interface Presenter {
        val theater: Theater
        val seatSelectSystem: SeatSelectSystem
        val priceSystem: PriceSystem
        fun onSeatClick(row: Int, col: Int)
        fun onReserveClick()
        fun getSeatInfoUiModel(colorOfGrade: Map<Grade, Int>): SeatInfoUiModel
    }
}
