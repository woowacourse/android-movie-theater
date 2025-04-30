package woowacourse.movie.presentation.view.reservation.seat

import woowacourse.movie.domain.model.cinema.DiceCinemaPricePolicy
import woowacourse.movie.domain.model.cinema.screen.Screen
import woowacourse.movie.domain.model.cinema.ticket.TicketBundle
import woowacourse.movie.domain.model.cinema.ticket.TicketMachine
import woowacourse.movie.domain.model.reservation.ReservationInfo
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.model.ScreenUiModel
import woowacourse.movie.presentation.model.SeatUiModel
import woowacourse.movie.presentation.model.toModel
import woowacourse.movie.presentation.model.toUiModel

class ReservationSeatPresenter(
    private val view: ReservationSeatContract.View,
) : ReservationSeatContract.Presenter {
    private val machine: TicketMachine = TicketMachine(DiceCinemaPricePolicy())
    private var reservationInfo: ReservationInfo? = null
    private lateinit var theaterName: String

    override fun fetchData(
        reservationInfo: ReservationInfoUiModel,
        screen: ScreenUiModel?,
        restoredSeats: ScreenUiModel?,
    ) {
        this.reservationInfo = reservationInfo.toModel()
        restoreSelectedSeats(restoredSeats)
        this.theaterName = reservationInfo.theaterName
        view.showScreen(
            reservationInfo,
            screen ?: Screen.DEFAULT_SCREEN.toUiModel(),
            this.reservationInfo?.seats?.map { it.toUiModel() } ?: emptyList(),
            publishTicketBundle()?.totalPrice ?: 0,
            canPublish(),
        )
    }

    override fun updateSeat(seat: SeatUiModel) {
        runCatching {
            reservationInfo?.updateSeats(seat.toModel())
        }.onFailure {
            view.notifySeatUpdateFailed(it.message.orEmpty())
            return
        }

        view.updateSeatState(
            seat,
            publishTicketBundle()?.totalPrice ?: TicketBundle.DEFAULT_TOTAL_PRICE,
            canPublish(),
        )
    }

    override fun publishTickets() {
        publishTicketBundle()?.let {
            view.notifyPublishedTickets(it.toUiModel(theaterName))
            return
        }
    }

    private fun restoreSelectedSeats(restoredSeats: ScreenUiModel?) {
        restoredSeats?.seats?.forEach { reservationInfo?.updateSeats(it.toModel()) }
    }

    private fun canPublish(): Boolean = reservationInfo?.canPublish() ?: false

    private fun publishTicketBundle(): TicketBundle? {
        runCatching {
            reservationInfo?.let { machine.publishTickets(it) }
        }.onSuccess {
            return it
        }

        return null
    }
}
