package woowacourse.movie.domain.model

import woowacourse.movie.domain.seat.Seats

data class Reservation(
    val movie: Movie,
    val reservationDetail: ReservationDetail,
    val seats: Seats,
    val price: Price
)
