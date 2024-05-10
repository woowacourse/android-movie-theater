package woowacourse.movie.data.reservation

import woowacourse.movie.model.ReservationCount
import java.time.LocalDate
import java.time.LocalTime

object GenerateReservation {
    private var id = 0L

    fun get(
        movieId: Long,
        screeningDate: LocalDate,
        screeningTime: LocalTime,
        reservationCount: ReservationCount,
        theaterName: String,
    ): Reservation {
        return Reservation(
            id++,
            movieId,
            screeningDate,
            screeningTime,
            reservationCount,
            theaterName
        )
    }
}
