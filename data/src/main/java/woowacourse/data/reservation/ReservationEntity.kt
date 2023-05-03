package woowacourse.data.reservation

import woowacourse.data.movie.MovieEntity
import java.time.LocalDateTime

data class ReservationEntity(
    val id: Long,
    val movie: MovieEntity,
    val bookedDateTime: LocalDateTime,
    val paymentType: Int,
    val seats: List<SeatEntity>,
)
