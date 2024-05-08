package woowacourse.movie.data

import woowacourse.movie.model.Advertisement
import woowacourse.movie.model.Movie
import woowacourse.movie.model.Reservation
import woowacourse.movie.model.Screening
import woowacourse.movie.model.ScreeningSchedule
import woowacourse.movie.model.Seats
import woowacourse.movie.model.Theater
import woowacourse.movie.repository.MovieRepository
import java.time.LocalDateTime

object DummyMovieRepository : MovieRepository {
    private val screeningSchedules: List<ScreeningSchedule> =
        listOf(
            ScreeningSchedule.STUB_A,
            ScreeningSchedule.STUB_B,
            ScreeningSchedule.STUB_C,
            ScreeningSchedule.STUB_D,
            ScreeningSchedule.STUB_E,
        )

    private val screenings: List<Screening> by lazy {
        var id = 0L
        val listA =
            (1..3).flatMap { day ->
                (9..15 step 3).map { time ->
                    id++
                    Screening(id, Movie.STUB_A, Theater.STUB_A, LocalDateTime.of(2024, 3, day, time, 0))
                }
            }

        val listB =
            (1..3).flatMap { day ->
                (9..15 step 3).map { time ->
                    id++
                    Screening(id, Movie.STUB_A, Theater.STUB_B, LocalDateTime.of(2024, 3, day, time, 0))
                }
            }

        val listC =
            (1..3).flatMap { day ->
                (9..15 step 3).map { time ->
                    id++
                    Screening(id, Movie.STUB_A, Theater.STUB_C, LocalDateTime.of(2024, 3, day, time, 0))
                }
            }

        val listD =
            (1..3).flatMap { day ->
                (9..15 step 3).map { time ->
                    id++
                    Screening(id, Movie.STUB_B, Theater.STUB_C, LocalDateTime.of(2024, 3, day, time, 0))
                }
            }

        val listE =
            (1..3).flatMap { day ->
                (9..15 step 3).map { time ->
                    id++
                    Screening(id, Movie.STUB_C, Theater.STUB_C, LocalDateTime.of(2024, 3, day, time, 0))
                }
            }

        listA + listB + listC + listD + listE
    }

    private val theaters: List<Theater> = listOf(Theater.STUB_A, Theater.STUB_B, Theater.STUB_C)
    private var reservations: List<Reservation> = emptyList()
    private var reservationId: Long = 0

    override fun movies(): List<Movie> = screeningSchedules.map { it.movie }.distinct()

    override fun advertisements(): List<Advertisement> = List(10) { Advertisement() }

    override fun screeningById(id: Long): Screening? = screenings[id.toInt()]

    override fun screeningsByMovieIdAndTheaterId(
        movieId: Long,
        theaterId: Long,
    ): List<Screening> = screenings.filter { it.movie.id == movieId && it.theater.id == theaterId }

    override fun screeningScheduleById(id: Long): ScreeningSchedule? = screeningSchedules.firstOrNull { it.id == id }

    override fun screeningScheduleByMovieIdAndTheaterId(
        movieId: Long,
        theaterId: Long,
    ): ScreeningSchedule? =
        screeningSchedules.firstOrNull {
            it.movie.id == movieId && it.theater.id == theaterId
        }

    override fun theatersByMovieId(movieId: Long): List<Theater> =
        screeningSchedules.filter {
            it.movie.id == movieId
        }.map { it.theater }

    override fun theaterById(theaterId: Long): Theater? = theaters.firstOrNull { it.id == theaterId }

    override fun makeReservation(
        screening: Screening,
        seats: Seats,
    ): Long {
        reservations +=
            Reservation(
                id = ++reservationId,
                screening = screening,
                seats = seats,
            )
        return reservationId
    }

    override fun reservationById(id: Long): Reservation? = reservations.find { it.id == id }
}
