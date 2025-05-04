package woowacourse.movie.presentation.home.reservation.seat

import woowacourse.movie.presentation.common.model.ReservationInfoUiModel
import woowacourse.movie.presentation.common.model.ScreenUiModel
import woowacourse.movie.presentation.common.model.SeatUiModel
import woowacourse.movie.presentation.common.model.TicketBundleUiModel

interface ReservationSeatContract {
    interface Presenter {
        fun fetchData(
            reservationInfo: ReservationInfoUiModel,
            screen: ScreenUiModel?,
            restoredSeats: ScreenUiModel?,
        )

        fun updateSeat(seat: SeatUiModel)

        fun publishTickets()
    }

    interface View {
        fun showScreen(
            reservationInfo: ReservationInfoUiModel,
            screen: ScreenUiModel,
            selectedSeats: List<SeatUiModel>,
        )

        fun updateSeatState(selectedSeat: SeatUiModel)

        fun notifyTotalPrice(totalPrice: Int)

        fun notifyCanPublish(canPublish: Boolean)

        fun notifyPublishedTickets(ticketBundle: TicketBundleUiModel)

        fun notifySeatUpdateFailed(message: String)
    }
}
