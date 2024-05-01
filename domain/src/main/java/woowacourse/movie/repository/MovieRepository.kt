package woowacourse.movie.repository

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.MovieReservation
import woowacourse.movie.model.MovieTheater
import woowacourse.movie.model.ReserveSeats
import woowacourse.movie.model.ScreeningMovie
import java.time.LocalDateTime

interface MovieRepository {
    fun screenMovies(): List<ScreeningMovie>

    fun screenMovieById(id: Long): ScreeningMovie

    fun screenMoviesById(movieId: Long, theaterId: Long): List<ScreeningMovie>

    fun theaterById(id: Long): MovieTheater

    fun reserveMovie(
        screenMovieId: Long,
        dateTime: LocalDateTime,
        count: HeadCount,
        seats: ReserveSeats,
        theaterId: Long,
    ): Long

    fun movieReservationById(id: Long): MovieReservation
}
