package woowacourse.movie.repository

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Movie
import woowacourse.movie.model.MovieReservation
import woowacourse.movie.model.MovieTheater
import woowacourse.movie.model.ScreeningMovie
import woowacourse.movie.model.Seat
import woowacourse.movie.model.SelectedSeats
import java.time.LocalDateTime

interface MovieRepository {
    fun movies(): List<Movie>

    fun screenMovieById(id: Long): ScreeningMovie

    fun screenMovieById(
        movieId: Long,
        theaterId: Long,
    ): ScreeningMovie

    fun theatersByMovieId(movieId: Long): List<MovieTheater>

    fun theaterById(theaterId: Long): MovieTheater

    fun reserveMovie(
        screenMovieId: Long,
        dateTime: LocalDateTime,
        count: HeadCount,
        seats: SelectedSeats,
        theaterId: Long,
    ): Long

    fun movieReservationById(id: Long): MovieReservation

    fun seatByTheaterId(
        row: Int,
        col: Int,
        theaterId: Long,
    ): Seat

    fun reservations(): List<MovieReservation>
}
