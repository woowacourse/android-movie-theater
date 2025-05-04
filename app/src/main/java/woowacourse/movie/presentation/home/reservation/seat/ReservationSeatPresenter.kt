package woowacourse.movie.presentation.home.reservation.seat

import woowacourse.movie.domain.model.cinema.Seats
import woowacourse.movie.domain.model.reservation.ReservationInfo
import woowacourse.movie.domain.model.ticketing.DiceCinemaPricePolicy
import woowacourse.movie.domain.model.ticketing.PricePolicy
import woowacourse.movie.domain.model.ticketing.TicketMachine
import woowacourse.movie.presentation.common.model.ReservationInfoUiModel
import woowacourse.movie.presentation.common.model.ScreenUiModel
import woowacourse.movie.presentation.common.model.SeatUiModel
import woowacourse.movie.presentation.common.model.toDomain
import woowacourse.movie.presentation.common.model.toUiModel

class ReservationSeatPresenter(
    private val view: ReservationSeatContract.View,
    policy: PricePolicy = DiceCinemaPricePolicy(),
) : ReservationSeatContract.Presenter {
    private val machine = TicketMachine(policy)
    private lateinit var reservationInfo: ReservationInfo
    private lateinit var theaterName: String

    override fun fetchData(
        reservationInfo: ReservationInfoUiModel,
        screen: ScreenUiModel?,
        restoredSeats: ScreenUiModel?,
    ) {
        this.reservationInfo = reservationInfo.toDomain()
        this.theaterName = reservationInfo.theaterName
        restoreSelectedSeats(restoredSeats)

        view.showScreen(
            reservationInfo,
            screen ?: Seats.DEFAULT_SEATS.toUiModel(),
            this.reservationInfo.seats.map { it.toUiModel() },
        )

        updateSeatEvent()
    }

    override fun updateSeat(seat: SeatUiModel) {
        runCatching {
            reservationInfo.updateSeats(seat.toDomain())
        }.onFailure {
            view.notifySeatUpdateFailed(it.message.orEmpty())
        }.onSuccess {
            view.updateSeatState(seat)
            updateSeatEvent()
        }
    }

    override fun publishTickets() {
        runCatching {
            machine.publishTickets(reservationInfo, theaterName)
        }.onSuccess {
            view.notifyPublishedTickets(it.toUiModel())
        }
    }

    private fun updateSeatEvent() {
        view.notifyTotalPrice(machine.calculateTotalPrice(reservationInfo.seats))
        view.notifyCanPublish(reservationInfo.canPublish())
    }

    private fun restoreSelectedSeats(restoredSeats: ScreenUiModel?) {
        restoredSeats?.seats?.forEach {
            reservationInfo.updateSeats(it.toDomain())
        }
    }
}
