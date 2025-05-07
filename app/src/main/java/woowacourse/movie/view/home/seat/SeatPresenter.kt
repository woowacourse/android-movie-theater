package woowacourse.movie.view.home.seat

import woowacourse.movie.domain.model.booking.Booking
import woowacourse.movie.domain.model.seat.Col
import woowacourse.movie.domain.model.seat.Row
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.domain.model.seat.Seats
import woowacourse.movie.domain.model.ticket.Ticket

class SeatPresenter(
    private val view: SeatContract.View,
    val seats: Seats,
    private val booking: Booking,
) : SeatContract.Presenter {
    private val limit = booking.count.value

    override fun loadBookingInfo() {
        view.showBookingInformation(booking.movieTitle)
        view.showPrice(seats.totalPrice())
    }

    override fun changeSeat(position: Seat) {
        val newSeat = Seat(Col(position.col.value), Row(position.row.value))

        if (!seats.isSelected(newSeat) && !seats.canSelect(limit)) {
            return view.notifySelectedSeatsCount(limit)
        }
        seats.toggleSeat(newSeat)

        view.showSeats(seats.item)
        view.showPrice(seats.totalPrice())
        updateConfirmControlState(limit)
    }

    override fun attemptConfirmBooking() {
        if (seats.isNotSelectDone(limit)) {
            return view.notifySelectedSeatsCount(limit)
        }

        val ticket = Ticket.initialize(booking, seats.item, seats.totalPrice())
        view.moveToBookingComplete(ticket)
    }

    private fun updateConfirmControlState(peopleCount: Int) {
        val isEnabled = seats.item.size == peopleCount
        view.setConfirmControlEnabled(isEnabled)
    }
}
