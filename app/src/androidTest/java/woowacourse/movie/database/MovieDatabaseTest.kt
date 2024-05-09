package woowacourse.movie.database

import androidx.room.Room
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import woowacourse.movie.data.dao.MovieDao
import woowacourse.movie.data.dao.MovieReservationDao
import woowacourse.movie.data.dao.MovieTheaterDao
import woowacourse.movie.data.dao.ScreeningMovieDao
import woowacourse.movie.data.database.MovieReservationDatabase
import woowacourse.movie.data.entity.Movie
import woowacourse.movie.data.entity.MovieReservation
import woowacourse.movie.data.entity.MovieTheater
import woowacourse.movie.data.entity.ScreeningMovie
import woowacourse.movie.fixtures.context
import java.io.IOException

class MovieDatabaseTest {
    private lateinit var movieReservationDao: MovieReservationDao
    private lateinit var movieDao: MovieDao
    private lateinit var theaterDao: MovieTheaterDao
    private lateinit var screeningMovieDao: ScreeningMovieDao

    private lateinit var db: MovieReservationDatabase

    @Before
    fun setUp() {
        db =
            Room.inMemoryDatabaseBuilder(
                context, MovieReservationDatabase::class.java,
            ).build()
        movieDao = db.movieDao()
        theaterDao = db.movieTheaterDao()
        movieReservationDao = db.movieReservationDao()
        screeningMovieDao = db.screeningMovieDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @DisplayName("예약 내역을 저장하면 다시 꺼낼 수 있다")
    fun can_get_stored_reservation_list() {
        // when
        movieDao.insert(Movie.STUB)
        theaterDao.insertAll(MovieTheater.STUB_A, MovieTheater.STUB_B, MovieTheater.STUB_C)

        // given
        movieReservationDao.insertAll(MovieReservation.STUB)

        // then
        val reservation = movieReservationDao.getAll()
        assertThat(reservation).contains(MovieReservation.STUB)
    }

    @Test
    @DisplayName("id를 이용해 예약 내역을 조회할 수 있다")
    fun get_reservation_Using_id() {
        // when
        movieDao.insert(Movie.STUB)
        theaterDao.insertAll(MovieTheater.STUB_A, MovieTheater.STUB_B, MovieTheater.STUB_C)
        movieReservationDao.insertAll(MovieReservation.STUB)

        // given
        val reservation = movieReservationDao.getMovieReservationById(MovieReservation.STUB.id)

        // then
        assertThat(reservation).isEqualTo(MovieReservation.STUB)
    }

    @Test
    @DisplayName("screen_movie를_저장하면_다시_꺼낼_수_있다")
    fun can_get_stored_Screen_movie() {
        // when
        screeningMovieDao.insertAll(ScreeningMovie.STUB)

        // given
        val screenMovie = screeningMovieDao.getAll()

        // then
        assertThat(screenMovie).contains(ScreeningMovie.STUB)
    }

    @Test
    @DisplayName("movieId와 theaterId를 이용해 screeningMovie를 조회할 수 있다")
    fun get_screen_movie_Useing_movieId_and_theaterId() {
        // when
        screeningMovieDao.insertAll(ScreeningMovie.STUB)

        // given
        val screenMovie =
            screeningMovieDao.getByMovieIdAndTheaterId(Movie.STUB.id, MovieTheater.STUB_A.id)

        // then
        assertThat(screenMovie).isEqualTo(ScreeningMovie.STUB)
    }
}
