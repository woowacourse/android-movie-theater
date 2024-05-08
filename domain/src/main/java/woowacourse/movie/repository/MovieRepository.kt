package woowacourse.movie.repository

import woowacourse.movie.model.Advertisement
import woowacourse.movie.model.Movie
import woowacourse.movie.model.Reservation
import woowacourse.movie.model.Screening
import woowacourse.movie.model.Seats
import woowacourse.movie.model.Theater
import java.time.LocalDateTime

interface MovieRepository {
    fun movies(): List<Movie>

    fun advertisements(): List<Advertisement>

    fun screeningById(id: Long): Screening?

    fun screeningByMovieIdAndTheaterId(
        movieId: Long,
        theaterId: Long,
    ): Screening?

    fun theaterById(theaterId: Long): Theater?

    fun theatersByMovieId(movieId: Long): List<Theater>

    fun makeReservation(
        screening: Screening,
        dateTime: LocalDateTime,
        seats: Seats,
    ): Long

    fun reservationById(id: Long): Reservation?
}
