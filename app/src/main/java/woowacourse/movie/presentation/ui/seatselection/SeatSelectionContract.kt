package woowacourse.movie.presentation.ui.seatselection

import woowacourse.movie.domain.model.SeatModel
import woowacourse.movie.presentation.base.BaseView
import woowacourse.movie.presentation.model.ReservationInfo

interface SeatSelectionContract {
    interface View : BaseView {
        fun showTotalPrice(totalPrice: Int)

        fun navigateToReservation(id: Int)

        fun showReservationDialog()

        fun back()
    }

    interface Presenter {
        fun updateUiModel(reservationInfo: ReservationInfo)

        fun loadScreen(id: Int)

        fun loadSeatBoard(id: Int)

        fun clickSeat(seatModel: SeatModel)

        fun calculateSeat()

        fun showConfirmDialog()

        fun reserve()
    }
}
