package woowacourse.movie.presentation.ui.seatselection

import woowacourse.movie.domain.model.SeatModel
import woowacourse.movie.presentation.base.BaseView
import woowacourse.movie.presentation.model.ReservationInfo

interface SeatSelectionContract {
    interface View : BaseView, SeatSelectionActionHandler {
        fun showSeatModel(seatModel: SeatSelectionUiModel)

        fun navigateToReservation(reservationId: Int)

        fun showReservationDialog()

        fun terminateOnError(e: Throwable)
    }

    interface Presenter {
        fun updateUiModel(
            reservationInfo: ReservationInfo,
            movieId: Int,
        )

        fun clickSeat(seatModel: SeatModel)

        fun calculateSeat()

        fun reserve()
    }
}
