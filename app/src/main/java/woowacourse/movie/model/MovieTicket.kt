package woowacourse.movie.model

import java.time.LocalDate
import java.time.LocalTime

data class MovieTicket(
    val movieTitle: String,
    val screeningDate: LocalDate,
    val screeningTime: LocalTime,
    val reservationCount: Int,
    val movieSelectedSeats: MovieSelectedSeats,
    val theaterName: String,
)
