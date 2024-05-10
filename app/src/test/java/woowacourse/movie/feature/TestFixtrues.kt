package woowacourse.movie.feature

import woowacourse.movie.data.movie.MovieRepository
import woowacourse.movie.data.reservation.Reservation
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.model.MovieSelectedSeats
import woowacourse.movie.model.ReservationCount
import java.time.LocalDate
import java.time.LocalTime

val firstMovieId = 0L
val firstMovie = MovieRepository.getMovieById(0L)
val invalidMovieId = -1L

val movieId = 0L
val screeningDate = LocalDate.of(2024, 4, 1)
val screeningTime = LocalTime.of(12, 30)
val selectedSeats = MovieSelectedSeats(3)
val theaterName = "선릉"

val ticket = Ticket(0L, movieId, screeningDate, screeningTime, selectedSeats, theaterName)

val reservationCount = ReservationCount(3)
val reservation =
    Reservation(0L, movieId, screeningDate, screeningTime, reservationCount, theaterName)
