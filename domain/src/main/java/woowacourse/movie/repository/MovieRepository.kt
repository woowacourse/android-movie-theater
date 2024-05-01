package woowacourse.movie.repository

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Movie
import woowacourse.movie.model.MovieReservation
import woowacourse.movie.model.MovieTheater
import woowacourse.movie.model.ReserveSeats
import woowacourse.movie.model.ScreeningMovie
import java.time.LocalDateTime

interface MovieRepository {
    fun movies(): List<Movie>

    fun screenMovieById(id: Long): ScreeningMovie

    fun screenMovieById(
        movieId: Long,
        theaterId: Long,
    ): ScreeningMovie

    fun theatersByMovieId(movieId: Long): List<MovieTheater>

    fun reserveMovie(
        screenMovieId: Long,
        dateTime: LocalDateTime,
        count: HeadCount,
        seats: ReserveSeats,
        theaterId: Long,
    ): Long

    fun movieReservationById(id: Long): MovieReservation
}
