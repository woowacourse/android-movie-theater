package woowacourse.movie.view.seat

import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.domain.model.seat.Seat

interface SeatContract {
    interface View {
        fun showSeat(seats: Set<Seat>)

        fun showToast(peopleCount: Int)

        fun showPrice(price: Int)

        fun setConfirmButtonEnabled(clickable: Boolean)

        fun moveToBookingComplete(id: Long)
    }

    interface Presenter {
        fun loadBookingInfo()

        fun changeSeat(position: Seat)

        fun attemptConfirmBooking()

        fun restore(seat: ArrayList<Seat>)
    }
}
