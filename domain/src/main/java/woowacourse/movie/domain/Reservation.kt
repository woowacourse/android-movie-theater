package woowacourse.movie.domain

import woowacourse.movie.domain.seat.Seats

data class Reservation(
    val movie: Movie,
    val reservationDetail: ReservationDetail,
    val seats: Seats,
    val price: Price
)
