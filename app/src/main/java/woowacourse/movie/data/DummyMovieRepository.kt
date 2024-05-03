package woowacourse.movie.data

import woowacourse.movie.model.Advertisement
import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Movie
import woowacourse.movie.model.MovieTheater
import woowacourse.movie.model.Reservation
import woowacourse.movie.model.ReserveSeats
import woowacourse.movie.model.Screening
import woowacourse.movie.repository.MovieRepository
import java.time.LocalDateTime

object DummyMovieRepository : MovieRepository {
    private val screenings: List<Screening> =
        listOf(
            Screening.STUB_A,
            Screening.STUB_B,
            Screening.STUB_C,
        )

    private val theaters: List<MovieTheater> =
        listOf(MovieTheater.STUB_A, MovieTheater.STUB_B, MovieTheater.STUB_C)
    private var reservations: List<Reservation> = emptyList()
    private var reservationId: Long = 0

    override fun movies(): List<Movie> = screenings.map { it.movie }.distinct()

    override fun advertisements(): List<Advertisement> = List(10) { Advertisement() }

    override fun screeningById(id: Long): Screening {
        return screenings.firstOrNull { it.id == id } ?: error(
            IdError.NO_MOVIE.message.format(id),
        )
    }

    override fun screeningByMovieIdAndTheaterId(
        movieId: Long,
        theaterId: Long,
    ): Screening =
        screenings.firstOrNull {
            it.movie.id == movieId && it.theater.id == theaterId
        } ?: error(
            "mola",
        )

    override fun theatersByMovieId(movieId: Long): List<MovieTheater> =
        screenings.filter {
            it.movie.id == movieId
        }.map { it.theater }

    override fun theaterById(theaterId: Long): MovieTheater = theaters.firstOrNull { it.id == theaterId } ?: error("id에 해당하는 극장이 없습니다.")

    override fun makeReservation(
        screenMovieId: Long,
        dateTime: LocalDateTime,
        count: HeadCount,
        seats: ReserveSeats,
        theaterId: Long,
    ): Long {
        reservations +=
            Reservation(
                id = ++reservationId,
                screening = screeningById(screenMovieId),
                screenDateTime = dateTime,
                headCount = count,
                reserveSeats = seats,
                theaterId = theaterId,
            )
        return reservationId
    }

    override fun reservationById(id: Long): Reservation {
        return reservations.find { it.id == id } ?: error(
            IdError.NO_RESERVATION.message.format(id),
        )
    }
}
