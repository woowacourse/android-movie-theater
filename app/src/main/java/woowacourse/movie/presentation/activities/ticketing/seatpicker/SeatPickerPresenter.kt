package woowacourse.movie.presentation.activities.ticketing.seatpicker

import woowacourse.movie.domain.model.discount.policy.MovieDayDiscountPolicy
import woowacourse.movie.domain.model.discount.policy.MovieTimeDiscountPolicy
import woowacourse.movie.domain.model.seat.DomainPickedSeats
import woowacourse.movie.presentation.mapper.toDomain
import woowacourse.movie.presentation.mapper.toPresentation
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.PickedSeats
import woowacourse.movie.presentation.model.Seat
import woowacourse.movie.presentation.model.SeatColumn
import woowacourse.movie.presentation.model.SeatRow
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.TicketPrice

class SeatPickerPresenter(
    val view: SeatPickerContract.View,
) : SeatPickerContract.Presenter {

    private var pickedSeats = DomainPickedSeats()

    override fun updateMovieTitle() {
        view.setMovieTitle()
    }

    override fun updateDoneBtnEnabled(isEnabled: Boolean) {
        view.setDoneBtnEnabled(isEnabled)
    }

    override fun updateTotalPriceView(ticketPrice: TicketPrice) {
        view.setTotalPriceView(ticketPrice)
    }

    override fun calculateTotalPrice(movieDate: MovieDate, movieTime: MovieTime): TicketPrice {
        return pickedSeats.calculateTotalPrice(
            MovieDayDiscountPolicy(movieDate.toDomain()),
            MovieTimeDiscountPolicy(movieTime.toDomain()),
        ).toPresentation()
    }

    override fun canPick(ticket: Ticket): Boolean {
        return pickedSeats.canPick(ticket.toDomain())
    }

    override fun pick(seat: Seat) {
        pickedSeats = pickedSeats.add(seat.toDomain())
    }

    override fun unPick(seat: Seat) {
        pickedSeats = pickedSeats.remove(seat.toDomain())
    }

    override fun onConfirmButtonClick() {
        view.showTicketingConfirmDialog()
    }

    override fun getSeat(row: SeatRow, col: SeatColumn): Seat {
        return Seat(row, col)
    }

    override fun isPicked(seat: Seat): Boolean {
        return pickedSeats.isPicked(seat.toDomain())
    }

    override fun getPickedSeats(): PickedSeats {
        return pickedSeats.toPresentation()
    }

    override fun setPickedSeats(pickedSeats: PickedSeats) {
        this.pickedSeats = pickedSeats.toDomain()
    }
}
