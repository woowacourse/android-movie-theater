package woowacourse.movie.view.home.seat

import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.domain.model.Ticket

interface SeatContract {
    interface View {
        fun showSeat(seats: Set<Seat>)

        fun showToast(peopleCount: Int)

        fun showPrice(price: Int)

        fun setConfirmButtonEnabled(clickable: Boolean)

        fun moveToBookingComplete(ticket: Ticket)
    }

    interface Presenter {
        fun loadBookingInfo()

        fun changeSeat(position: Seat)

        fun attemptConfirmBooking()

        fun restore(seat: ArrayList<Seat>)
    }
}
