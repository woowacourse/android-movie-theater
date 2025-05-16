package woowacourse.movie.presentation.home.reservation.seat

import woowacourse.movie.RepositoryProvider
import woowacourse.movie.domain.ReservationRepository
import woowacourse.movie.domain.model.cinema.Seat
import woowacourse.movie.domain.model.cinema.Seats
import woowacourse.movie.domain.model.reservation.ReservationInfo
import woowacourse.movie.domain.model.ticketing.DiceCinemaPricePolicy
import woowacourse.movie.domain.model.ticketing.PricePolicy
import woowacourse.movie.domain.model.ticketing.Ticket
import woowacourse.movie.domain.model.ticketing.TicketMachine
import woowacourse.movie.presentation.common.model.ReservationInfoUiModel
import woowacourse.movie.presentation.common.model.ScreenUiModel
import woowacourse.movie.presentation.common.model.SeatUiModel
import woowacourse.movie.presentation.common.model.toDomain
import woowacourse.movie.presentation.common.model.toUiModel

class ReservationSeatPresenter private constructor(
    private val view: ReservationSeatContract.View,
    private val reservationRepository: ReservationRepository,
    policy: PricePolicy,
) : ReservationSeatContract.Presenter {
    private val machine = TicketMachine(policy)
    private lateinit var reservationInfo: ReservationInfo

    override fun fetchData(
        reservationInfo: ReservationInfoUiModel,
        screen: ScreenUiModel?,
        restoredSeats: ScreenUiModel?,
    ) {
        this.reservationInfo = reservationInfo.toDomain()
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
            val domainSeat = seat.toDomain()
            validUpdateSeat(domainSeat)
        }.onFailure {
            view.notifySeatUpdateFailed(it.message.orEmpty())
        }.onSuccess {
            view.updateSeatState(seat)
            updateSeatEvent()
        }
    }

    override fun publishTickets() {
        runCatching {
            machine.publishTickets(reservationInfo)
        }.onSuccess {
            saveReservationHistory(it)
        }
    }

    private fun validUpdateSeat(seat: Seat) {
        with(reservationInfo) {
            if (hasSeat(seat)) removeSeat(seat) else addSeat(seat)
        }
    }

    private fun updateSeatEvent() {
        view.updateTotalPrice(machine.calculateTotalPrice(reservationInfo.seats))
        view.notifyCanPublish(reservationInfo.canPublish())
    }

    private fun restoreSelectedSeats(restoredSeats: ScreenUiModel?) {
        restoredSeats?.seats?.forEach {
            reservationInfo.addSeat(it.toDomain())
        }
    }

    private fun saveReservationHistory(ticket: Ticket) {
        reservationRepository.insert(ticket) { result ->
            result
                .onSuccess { view.notifyPublishedTicketSuccess(ticket.toUiModel()) }
                .onFailure { view.notifyPublishTicketFailed() }
        }
    }

    companion object {
        fun create(
            view: ReservationSeatContract.View,
            reservationRepository: ReservationRepository = RepositoryProvider.reservationRepository,
            policy: PricePolicy = DiceCinemaPricePolicy(),
        ): ReservationSeatPresenter = ReservationSeatPresenter(view, reservationRepository, policy)
    }
}
