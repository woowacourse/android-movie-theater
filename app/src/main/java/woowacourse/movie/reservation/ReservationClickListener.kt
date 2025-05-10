package woowacourse.movie.reservation

import woowacourse.movie.domain.ReservationInfo

interface ReservationClickListener {
    fun clickReservation(reservationInfo: ReservationInfo)
}
