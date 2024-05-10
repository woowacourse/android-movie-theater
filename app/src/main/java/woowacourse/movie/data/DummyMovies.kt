package woowacourse.movie.data

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Movie
import woowacourse.movie.model.MovieReservation
import woowacourse.movie.model.MovieTheater
import woowacourse.movie.model.ScreeningMovie
import woowacourse.movie.model.Seat
import woowacourse.movie.model.SelectedSeats
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

    override fun movies(): List<Movie> = screenMovies.map { it.movie }.distinct()

    override fun screenMovieById(id: Long): ScreeningMovie {
        return screenMovies.firstOrNull { it.id == id } ?: error(
            IdError.NO_MOVIE.message.format(id),
        )
    }

    override fun screenMovieById(
        movieId: Long,
        theaterId: Long,
    ): ScreeningMovie =
        screenMovies.firstOrNull {
            it.movie.id == movieId && it.theater.id == theaterId
        } ?: error(
            "movieId: $movieId, theaterId: $theaterId 에 해당하는 ScreeningMovie가 없습니다.",
        )

    override fun theatersByMovieId(movieId: Long): List<MovieTheater> =
        screenMovies.filter {
            it.movie.id == movieId
        }.map { it.theater }

    override fun theaterById(theaterId: Long): MovieTheater = theaters.firstOrNull { it.id == theaterId } ?: error("id에 해당하는 극장이 없습니다.")

    override fun reserveMovie(
        screenMovieId: Long,
        dateTime: LocalDateTime,
        count: HeadCount,
        seats: SelectedSeats,
        theaterId: Long,
    ): Long {
        val theater = theaterById(theaterId)
        theater.reserveSeat(seats)

        reservations +=
            MovieReservation(
                id = ++reservationId,
                screeningMovie = screenMovieById(screenMovieId),
                screenDateTime = dateTime,
                headCount = count,
                selectedSeats = seats,
                theaterId = theaterId,
            )
        return reservationId
    }

    override fun movieReservationById(id: Long): MovieReservation {
        return reservations.find { it.id == id } ?: error(
            IdError.NO_RESERVATION.message.format(id),
        )
    }

    override fun seatByTheaterId(
        row: Int,
        col: Int,
        theaterId: Long,
    ): Seat =
        theaterById(theaterId).seats.firstOrNull { it.row == row && it.col == col }
            ?: error("해당 극장에는 row:$Int, col:${Int}에 해당하는 좌석이 없습니다.")

    override fun reservations(): List<MovieReservation> = reservations
}
