package woowacourse.movie.repository

import woowacourse.movie.model.Advertisement
import woowacourse.movie.model.Movie
import woowacourse.movie.model.Reservation
import woowacourse.movie.model.Screening
import woowacourse.movie.model.ScreeningSchedule
import woowacourse.movie.model.Seats
import woowacourse.movie.model.Theater

interface EverythingRepository {
    fun movieById(id: Long): Movie?

    fun movies(): List<Movie>

    fun advertisements(): List<Advertisement>

    fun screeningById(id: Long): Screening?

    fun screeningsByMovieIdAndTheaterId(
        movieId: Long,
        theaterId: Long,
    ): List<Screening>

    fun screeningScheduleById(id: Long): ScreeningSchedule?

    fun screeningScheduleByMovieIdAndTheaterId(
        movieId: Long,
        theaterId: Long,
    ): ScreeningSchedule?

    fun theaterById(theaterId: Long): Theater?

    fun theatersByMovieId(movieId: Long): List<Theater>

    fun makeReservation(
        screening: Screening,
        seats: Seats,
    ): Long

    fun reservationById(id: Long): Reservation?
}
