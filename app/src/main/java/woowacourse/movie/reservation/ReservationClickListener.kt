package woowacourse.movie.reservation

import woowacourse.movie.data.Reservation

interface ReservationClickListener {
    fun clickReservation(reservation: Reservation)
}
