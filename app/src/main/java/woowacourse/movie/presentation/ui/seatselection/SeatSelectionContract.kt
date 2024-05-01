package woowacourse.movie.presentation.ui.seatselection

import woowacourse.movie.domain.model.Seat
import woowacourse.movie.presentation.base.BasePresenter
import woowacourse.movie.presentation.base.BaseView
import woowacourse.movie.presentation.model.ReservationInfo

interface SeatSelectionContract {
    interface View : BaseView {
        fun selectSeat(
            column: Int,
            row: Int,
        )

        fun unselectSeat(
            column: Int,
            row: Int,
        )

        fun showTotalPrice(totalPrice: Int)

        fun navigateToReservation(id: Int)

        fun showReservationDialog()

        fun back()
    }

    interface Presenter : BasePresenter {
        fun updateUiModel(reservationInfo: ReservationInfo)

        fun loadScreen(id: Int)

        fun loadSeatBoard(id: Int)

        fun clickSeat(seat: Seat)

        fun calculateSeat()

        fun showConfirmDialog()

        fun reserve()
    }
}
