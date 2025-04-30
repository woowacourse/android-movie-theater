package woowacourse.movie.presentation.view.home.reservation.seat

import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.model.ScreenUiModel
import woowacourse.movie.presentation.model.SeatUiModel
import woowacourse.movie.presentation.model.TicketBundleUiModel

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
            totalPrice: Int,
            canPublish: Boolean,
        )

        fun updateSeatState(
            selectedSeat: SeatUiModel,
            totalPrice: Int,
            canPublish: Boolean,
        )

        fun notifyPublishedTickets(ticketBundle: TicketBundleUiModel)

        fun notifySeatUpdateFailed(message: String)
    }
}
