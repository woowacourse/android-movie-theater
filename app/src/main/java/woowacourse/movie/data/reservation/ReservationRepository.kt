package woowacourse.movie.data.reservation

import woowacourse.movie.model.ReservationCount
import java.time.LocalDate
import java.time.LocalTime

interface ReservationRepository {
    fun save(
        movieId: Long,
        screeningDate: LocalDate,
        screeningTime: LocalTime,
        reservationCount: ReservationCount,
        theaterName: String,
    ): Long

    fun find(id: Long): Reservation

    fun deleteAll()
}
