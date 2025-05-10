package woowacourse.movie.moviebooked

import woowacourse.movie.domain.ReservationInfo

interface MovieBookedContract {
    interface View {
        fun fetchReservationInfo()

        fun showReservationInfo(reservationInfo: ReservationInfo)
    }

    interface Presenter {
        fun loadReservationInfo(reservationInfo: ReservationInfo)
    }
}
