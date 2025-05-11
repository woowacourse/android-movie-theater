package woowacourse.movie.moviebookingseat

import android.content.Context
import woowacourse.movie.domain.BookingStatus
import woowacourse.movie.domain.Theater
import woowacourse.movie.domain.seat.Seat

interface MovieBookingSeat {
    interface View {
        fun showBookingStatusInfo()

        fun updateSeat(
            seat: Seat,
            isSelected: Boolean,
        )

        fun updateButton()

        fun showTotalPrice(price: Int)

        fun showConfirmDialog(id: Long)

        fun navigateToMovieBooked(id: Long)

        fun showError(messageRes: Int)
    }

    interface Presenter {
        fun loadBookingStatus(bookingStatus: BookingStatus, theater: Theater)

        fun selectSeat(seat: Seat)

        fun calculatePrice()

        fun confirmBooking(context: Context)
    }
}
