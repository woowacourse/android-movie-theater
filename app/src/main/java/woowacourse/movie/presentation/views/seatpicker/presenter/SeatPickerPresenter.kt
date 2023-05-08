package woowacourse.movie.presentation.views.seatpicker.presenter

import woowacourse.movie.domain.model.discount.policy.MovieDayDiscountPolicy
import woowacourse.movie.domain.model.discount.policy.MovieTimeDiscountPolicy
import woowacourse.movie.domain.model.movie.DomainTicketPrice
import woowacourse.movie.domain.model.repository.HistoryRepository
import woowacourse.movie.domain.model.seat.DomainPickedSeats
import woowacourse.movie.presentation.mapper.toDomain
import woowacourse.movie.presentation.mapper.toPresentation
import woowacourse.movie.presentation.model.PickedSeats
import woowacourse.movie.presentation.model.Seat
import woowacourse.movie.presentation.model.TicketingState
import woowacourse.movie.presentation.views.seatpicker.contract.SeatPickerContract

class SeatPickerPresenter(
    private val seatRowSize: Int = 5,
    private val seatColSize: Int = 4,
    view: SeatPickerContract.View,
    private val ticketingState: TicketingState,
    private val historyRepository: HistoryRepository,
) : SeatPickerContract.Presenter(view) {
    private var pickedSeats: DomainPickedSeats = DomainPickedSeats()

    init {
        view.showMovieTitle(ticketingState.movie.title)
        updatePriceTextAndReservationEnabled()
        view.initSeatTable(seatRowSize, seatColSize)
    }

    override fun getState(): PickedSeats = pickedSeats.toPresentation()

    override fun setState(state: PickedSeats) {
        pickedSeats = state.toDomain()
        updatePriceTextAndReservationEnabled()
        view.fetchPickedSeatViews(state.indices(seatColSize))
    }

    private fun updatePriceTextAndReservationEnabled() {
        view.updateTotalPriceView(calculateTotalPrice().toPresentation())
        view.updateReservationBtnEnabled(isAllPicked())
    }

    override fun reserveMovie() {
        val reservedDate = ticketingState.movieDate?.toDomain() ?: return
        val reservedTime = ticketingState.movieTime?.toDomain() ?: return
        val reservation = ticketingState.movie.toDomain().reserve(
            theaterName = ticketingState.theater.name,
            reservedDate = reservedDate,
            reservedTime = reservedTime,
            ticket = ticketingState.ticket.toDomain(),
            seats = pickedSeats,
            ticketPrice = calculateTotalPrice(),
        )

        historyRepository.add(reservation)
        view.registerPushBroadcast(reservation.toPresentation())
        view.showTicketingResultScreen(reservation.toPresentation())
    }

    override fun changeSeatState(seat: Seat): Unit = when {
        isPicked(seat) -> unpick(seat)
        !isAllPicked() -> pick(seat)
        else -> view.showSeatExceedAlertMessage()
    }

    private fun isPicked(seat: Seat): Boolean = pickedSeats.isPicked(seat.toDomain())

    private fun unpick(seat: Seat) {
        pickedSeats = pickedSeats.remove(seat.toDomain())
        updateViewWithSeat(seat, false)
    }

    override fun isAllPicked(): Boolean =
        !pickedSeats.canPick(ticketingState.ticket.toDomain())

    private fun pick(seat: Seat) {
        pickedSeats = pickedSeats.add(seat.toDomain())
        updateViewWithSeat(seat, true)
    }

    private fun updateViewWithSeat(seat: Seat, isPicked: Boolean) {
        updatePriceTextAndReservationEnabled()
        view.setSeatViewPickState(seat.toIndex(seatColSize), isPicked)
    }

    private fun calculateTotalPrice(): DomainTicketPrice = pickedSeats.calculateTotalPrice(
        MovieDayDiscountPolicy(ticketingState.movieDate?.toDomain()!!),
        MovieTimeDiscountPolicy(ticketingState.movieTime?.toDomain()!!),
    )
}
