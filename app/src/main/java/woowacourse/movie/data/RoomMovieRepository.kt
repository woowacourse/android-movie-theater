package woowacourse.movie.data

import woowacourse.movie.data.dao.MovieDao
import woowacourse.movie.data.dao.MovieReservationDao
import woowacourse.movie.data.dao.MovieTheaterDao
import woowacourse.movie.data.dao.ScreeningMovieDao
import woowacourse.movie.data.entity.MovieReservationEntity
import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Movie
import woowacourse.movie.model.MovieReservation
import woowacourse.movie.model.MovieTheater
import woowacourse.movie.model.ScreeningMovie
import woowacourse.movie.model.Seat
import woowacourse.movie.model.SelectedSeats
import woowacourse.movie.repository.MovieRepository
import java.time.LocalDateTime

class RoomMovieRepository(
    private val movieDao: MovieDao,
    private val theaterDao: MovieTheaterDao,
    private val screenMovieDao: ScreeningMovieDao,
    private val reservationDao: MovieReservationDao,
) : MovieRepository {
    override fun movies(): List<Movie> {
        return movieDao.getAll().map { it.toMovie() }
    }

    override fun screenMovieById(id: Long): ScreeningMovie {
        return screenMovieDao.getScreenMovieById(id).toScreeningMovie()
    }

    override fun screenMovieById(
        movieId: Long,
        theaterId: Long,
    ): ScreeningMovie {
        return screenMovieDao.getByMovieIdAndTheaterId(movieId, theaterId).toScreeningMovie()
    }

    override fun theatersByMovieId(movieId: Long): List<MovieTheater> {
        return screenMovieDao.getTheatersByMovieId(movieId).map { it.toMovieTheater() }
    }

    override fun theaterById(theaterId: Long): MovieTheater {
        return theaterDao.getMovieTheaterById(theaterId).toMovieTheater()
    }

    override fun reserveMovie(
        screenMovieId: Long,
        dateTime: LocalDateTime,
        count: HeadCount,
        seats: SelectedSeats,
        theaterId: Long,
    ): Long {
        val movie = screenMovieDao.getScreenMovieById(screenMovieId).movie
        return reservationDao.insert(
            MovieReservationEntity(
                screenMovieId,
                movie,
                seats.selectedSeats.map { mapOf(it.row to it.col) },
                dateTime,
                count.count,
                theaterId,
            ),
        )
    }

    override fun movieReservationById(id: Long): MovieReservation {
        return reservationDao.getMovieReservationById(id).toMovieReservation()
    }

    override fun seatByTheaterId(
        row: Int,
        col: Int,
        theaterId: Long,
    ): Seat {
        val theaters: List<MovieTheater> =
            listOf(MovieTheater.STUB_A, MovieTheater.STUB_B, MovieTheater.STUB_C)
        return theaters.firstOrNull { it.id == theaterId }?.seats?.firstOrNull { it.row == row && it.col == col }
            ?: error("해당 극장에는 row:$Int, col:${Int}에 해당하는 좌석이 없습니다.")
    }

    override fun reservations(): List<MovieReservation> {
        return reservationDao.getAll().map { it.toMovieReservation() }
    }

    companion object {
        private var instance: MovieRepository? = null

        fun initialize(
            movieDao: MovieDao,
            theaterDao: MovieTheaterDao,
            screenMovieDao: ScreeningMovieDao,
            reservationDao: MovieReservationDao,
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: RoomMovieRepository(
                    movieDao,
                    theaterDao,
                    screenMovieDao,
                    reservationDao,
                ).also { instance = it }
            }

        fun instance(): MovieRepository = instance ?: error("RoomRepo가 초기화되지 않았습니다.")
    }
}
