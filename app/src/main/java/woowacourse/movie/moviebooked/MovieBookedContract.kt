package woowacourse.movie.moviebooked

import woowacourse.movie.domain.BookingStatus
import woowacourse.movie.domain.ReservationInfo
import woowacourse.movie.domain.Theater

interface MovieBookedContract {
    interface View {
        fun fetchBookingStatus()

        fun showBookedStatus(
            bookingStatus: BookingStatus,
            theater: Theater,
        )

        fun fetchReservationInfo()

        fun showReservationInfo(reservationInfo: ReservationInfo)
    }

    interface Presenter {
        fun loadBookedStatus(
            bookingStatus: BookingStatus,
            theater: Theater,
        )

        fun loadReservationInfo(reservationInfo: ReservationInfo)
    }
}
