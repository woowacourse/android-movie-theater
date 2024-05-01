package woowacourse.movie.data

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.MovieReservation
import woowacourse.movie.model.MovieTheater
import woowacourse.movie.model.ReserveSeats
import woowacourse.movie.model.ScreeningMovie
import woowacourse.movie.repository.MovieRepository
import java.time.LocalDateTime

object DummyMovies : MovieRepository {
    private val screenMovies: List<ScreeningMovie> =
        listOf(
            ScreeningMovie.STUB_A,
            ScreeningMovie.STUB_B,
            ScreeningMovie.STUB_C,
        )

    private val theaters: List<MovieTheater> =
        listOf(MovieTheater.STUB_A, MovieTheater.STUB_B, MovieTheater.STUB_C)
    private var reservations: List<MovieReservation> = emptyList()
    private var reservationId: Long = 0

    override fun theaterById(theaterId: Long): MovieTheater {
        /*return theaters.firstOrNull{ it.id == id}?: error(
            IdError.NO_THEATER.message.format(id),
        )*/
        return MovieTheater.STUB_A
    }

    override fun screenMovies(): List<ScreeningMovie> = screenMovies

    override fun screenMovieById(id: Long): ScreeningMovie {
        return screenMovies.firstOrNull { it.id == id } ?: error(
            IdError.NO_MOVIE.message.format(id),
        )
    }

    override fun screenMoviesById(movieId: Long, theaterId: Long): List<ScreeningMovie> =
        screenMovies.filter {
            it.movie.id == movieId && it.theater.id == theaterId
        }

    override fun reserveMovie(
        screenMovieId: Long,
        dateTime: LocalDateTime,
        count: HeadCount,
        seats: ReserveSeats,
        theaterId: Long,
    ): Long {
        reservations +=
            MovieReservation(
                id = ++reservationId,
                screeningMovie = screenMovieById(screenMovieId),
                screenDateTime = dateTime,
                headCount = count,
                reserveSeats = seats,
                theaterId = theaterId,
            )
        return reservationId
    }

    override fun movieReservationById(id: Long): MovieReservation {
        return reservations.find { it.id == id } ?: error(
            IdError.NO_RESERVATION.message.format(id),
        )
    }
}
