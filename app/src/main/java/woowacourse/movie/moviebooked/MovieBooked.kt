package woowacourse.movie.moviebooked

import woowacourse.movie.domain.BookingStatus
import woowacourse.movie.domain.Theater

interface MovieBooked {
    interface View {
        fun fetchBookingStatus()

        fun showBookedStatus(bookingStatus: BookingStatus, theater: Theater)
    }

    interface Presenter {
        fun loadBookedStatus(bookingStatus: BookingStatus, theater: Theater)
    }
}
