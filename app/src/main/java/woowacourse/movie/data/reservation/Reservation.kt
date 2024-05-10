package woowacourse.movie.data.reservation

import woowacourse.movie.model.ReservationCount
import java.time.LocalDate
import java.time.LocalTime

class Reservation(
    val id: Long,
    val movieId: Long,
    val screeningDate: LocalDate,
    val screeningTime: LocalTime,
    val reservationCount: ReservationCount,
    val theaterName: String,
)
