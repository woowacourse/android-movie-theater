package woowacourse.movie.moviebooked

import woowacourse.movie.data.Reservation

interface MovieBookedContract {
    interface View {
        fun fetchReservationInfo()

        fun showReservation(reservation: Reservation)
    }

    interface Presenter {
        fun loadReservationInfo(id: Long)
    }
}
