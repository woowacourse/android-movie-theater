package woowacourse.movie.presentation.ui.seatselection

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.SeatModel
import woowacourse.movie.presentation.base.BasePresenter
import woowacourse.movie.presentation.base.BaseView
import woowacourse.movie.presentation.model.ReservationInfo
import woowacourse.movie.presentation.model.UserSeat

interface SeatSelectionContract {
    interface View : BaseView {
        fun showScreen(
            screen: Screen,
            totalPrice: Int,
            ticketCount: Int,
        )

        fun showSeatBoard(userSeat: UserSeat)

        fun selectSeat(userSeat: UserSeat)

        fun unselectSeat(userSeat: UserSeat)

        fun showTotalPrice(totalPrice: Int)

        fun navigateToReservation(id: Int)

        fun showReservationDialog()

        fun back()
    }

    interface Presenter : BasePresenter {
        fun updateUiModel(reservationInfo: ReservationInfo)

        fun loadScreen(id: Int)

        fun loadSeatBoard(id: Int)

        fun clickSeat(seatModel: SeatModel)

        fun calculateSeat()

        fun showConfirmDialog()

        fun reserve()
    }
}
