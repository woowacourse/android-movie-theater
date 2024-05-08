package woowacourse.movie.feature

import woowacourse.movie.data.entity.Ticket
import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import java.time.LocalDate
import java.time.LocalTime

val firstMovieId = 0L

val movieId = 0L
val screeningDate = LocalDate.of(2024, 4, 1)
val screeningTime = LocalTime.of(12, 30)
val selectedSeats =
    MovieSelectedSeats(3).apply {
        selectSeat(MovieSeat(1, 1))
        selectSeat(MovieSeat(2, 2))
        selectSeat(MovieSeat(3, 3))
    }
val theaterName = "선릉"

val ticket = Ticket(0L, movieId, screeningDate, screeningTime, selectedSeats, theaterName)
