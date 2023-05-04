package woowacourse.movie.view.seatselection

import woowacourse.movie.view.model.ReservationUiModel
import java.time.LocalDateTime

interface SeatSelectionContract {
    interface View {
        var presenter: Presenter
        fun setSelectionSeat(row: Int, col: Int, isClickableButton: Boolean)
        fun setDeselectionSeat(row: Int, col: Int)
        fun maxSelectionToast()
        fun wrongInputToast()
        fun setPrice(price: String)
        fun showSubmitDialog(reservation: ReservationUiModel)
    }

    interface Presenter {
        fun onSeatClick(row: Int, col: Int)
        fun onReserveClick(title: String, screeningDateTime: LocalDateTime)
    }
}
