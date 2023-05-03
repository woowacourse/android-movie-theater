package woowacourse.movie.presentation.activities.seatpicker.presenter

import woowacourse.movie.domain.model.discount.policy.MovieDayDiscountPolicy
import woowacourse.movie.domain.model.discount.policy.MovieTimeDiscountPolicy
import woowacourse.movie.domain.model.seat.DomainPickedSeats
import woowacourse.movie.presentation.activities.seatpicker.contract.SeatPickerContract
import woowacourse.movie.presentation.mapper.toDomain
import woowacourse.movie.presentation.mapper.toPresentation
import woowacourse.movie.presentation.model.PickedSeats
import woowacourse.movie.presentation.model.Seat
import woowacourse.movie.presentation.model.TicketPrice
import woowacourse.movie.presentation.model.TicketingState

class SeatPickerPresenter(
    private val seatRowSize: Int = 5,
    private val seatColSize: Int = 4,
    private val ticketingState: TicketingState
) : SeatPickerContract.Presenter() {
    private var pickedSeats: DomainPickedSeats = DomainPickedSeats()

    override fun attach(view: SeatPickerContract.View) {
        super.attach(view)
        requireView().showMovieTitle(ticketingState.movie.title)
        requireView().updateReservationBtnEnabled(isAllPicked())
        requireView().updateTotalPriceView(calculateTotalPrice())
        requireView().initSeatTable(seatRowSize, seatColSize)
    }

    override fun getState(): PickedSeats = pickedSeats.toPresentation()

    override fun setState(state: PickedSeats) {
        pickedSeats = state.toDomain()
        updatePriceTextAndReservationEnabled()
        requireView().fetchPickedSeatViews(state.indices(seatColSize))
    }

    private fun updatePriceTextAndReservationEnabled() {
        requireView().updateTotalPriceView(calculateTotalPrice())
        requireView().updateReservationBtnEnabled(isAllPicked())
    }

    override fun reserveMovie() {
        val reservation = ticketingState.movie.toDomain().reserve(
            reservedDate = ticketingState.movieDate?.toDomain()!!,
            reservedTime = ticketingState.movieTime?.toDomain()!!,
            ticket = ticketingState.ticket.toDomain(),
            seats = pickedSeats,
            ticketPrice = calculateTotalPrice().toDomain(),
        ).toPresentation()

        requireView().registerPushBroadcast(reservation)
        requireView().showTicketingResultScreen(reservation)
    }

    private fun pick(seat: Seat) {
        pickedSeats = pickedSeats.add(seat.toDomain())
        updateViewWithSeat(seat, true)
    }

    private fun unpick(seat: Seat) {
        pickedSeats = pickedSeats.remove(seat.toDomain())
        updateViewWithSeat(seat, false)
    }

    private fun updateViewWithSeat(seat: Seat, isPicked: Boolean) {
        updatePriceTextAndReservationEnabled()
        requireView().setSeatViewPickState(seat.toIndex(seatColSize), isPicked)
    }

    private fun isPicked(seat: Seat): Boolean = pickedSeats.isPicked(seat.toDomain())

    override fun onClickSeat(seat: Seat): Unit = when {
        isPicked(seat) -> unpick(seat)
        !isAllPicked() -> pick(seat)
        else -> requireView().showSeatExceedAlertMessage()
    }

    override fun isAllPicked(): Boolean =
        !pickedSeats.canPick(ticketingState.ticket.toDomain())

    private fun calculateTotalPrice(): TicketPrice = pickedSeats.calculateTotalPrice(
        MovieDayDiscountPolicy(ticketingState.movieDate?.toDomain()!!),
        MovieTimeDiscountPolicy(ticketingState.movieTime?.toDomain()!!),
    ).toPresentation()
}
