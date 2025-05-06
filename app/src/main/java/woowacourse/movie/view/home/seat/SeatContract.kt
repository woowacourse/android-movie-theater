package woowacourse.movie.view.home.seat

import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.domain.model.ticket.Ticket

interface SeatContract {
    interface View {
        fun showBookingInformation(title: String)

        fun showSeats(seats: Set<Seat>)

        fun notifySelectedSeatsCount(peopleCount: Int)

        fun showPrice(price: Int)

        fun setConfirmControlEnabled(clickable: Boolean)

        fun moveToBookingComplete(ticket: Ticket)
    }

    interface Presenter {
        fun loadBookingInfo()

        fun changeSeat(position: Seat)

        fun attemptConfirmBooking()
    }
}
