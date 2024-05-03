package woowacourse.movie.data

import woowacourse.movie.model.Advertisement
import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Movie
import woowacourse.movie.model.Reservation
import woowacourse.movie.model.Screening
import woowacourse.movie.model.Seats
import woowacourse.movie.model.Theater
import woowacourse.movie.repository.MovieRepository
import java.time.LocalDateTime

object DummyMovieRepository : MovieRepository {
    private val screenings: List<Screening> =
        listOf(
            Screening.STUB_A,
            Screening.STUB_B,
            Screening.STUB_C,
        )

    private val theaters: List<Theater> =
        listOf(Theater.STUB_A, Theater.STUB_B, Theater.STUB_C)
    private var reservations: List<Reservation> = emptyList()
    private var reservationId: Long = 0

    override fun movies(): List<Movie> = screenings.map { it.movie }.distinct()

    override fun advertisements(): List<Advertisement> = List(10) { Advertisement() }

    override fun screeningById(id: Long): Screening? = screenings.firstOrNull { it.id == id }

    override fun screeningByMovieIdAndTheaterId(
        movieId: Long,
        theaterId: Long,
    ): Screening? =
        screenings.firstOrNull {
            it.movie.id == movieId && it.theater.id == theaterId
        }

    override fun theatersByMovieId(movieId: Long): List<Theater> =
        screenings.filter {
            it.movie.id == movieId
        }.map { it.theater }

    override fun theaterById(theaterId: Long): Theater? = theaters.firstOrNull { it.id == theaterId }

    override fun makeReservation(
        screening: Screening,
        dateTime: LocalDateTime,
        count: HeadCount,
        seats: Seats,
        theaterId: Long,
    ): Long {
        reservations +=
            Reservation(
                id = ++reservationId,
                screening = screening,
                screenDateTime = dateTime,
                headCount = count,
                seats = seats,
                theaterId = theaterId,
            )
        return reservationId
    }

    override fun reservationById(id: Long): Reservation? = reservations.find { it.id == id }
}
