package woowacourse.movie.repository

import woowacourse.movie.model.Advertisement
import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Movie
import woowacourse.movie.model.MovieTheater
import woowacourse.movie.model.Reservation
import woowacourse.movie.model.ReserveSeats
import woowacourse.movie.model.Screening
import java.time.LocalDateTime

interface MovieRepository {
    fun movies(): List<Movie>

    fun advertisements(): List<Advertisement>

    fun screeningById(id: Long): Screening

    fun screeningByMovieIdAndTheaterId(
        movieId: Long,
        theaterId: Long,
    ): Screening

    fun theatersByMovieId(movieId: Long): List<MovieTheater>

    fun theaterById(theaterId: Long): MovieTheater

    fun makeReservation(
        screenMovieId: Long,
        dateTime: LocalDateTime,
        count: HeadCount,
        seats: ReserveSeats,
        theaterId: Long,
    ): Long

    fun reservationById(id: Long): Reservation
}
