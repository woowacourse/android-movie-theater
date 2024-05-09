package woowacourse.movie.database

import androidx.room.Room
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.dao.MovieDao
import woowacourse.movie.data.dao.MovieReservationDao
import woowacourse.movie.data.dao.MovieTheaterDao
import woowacourse.movie.data.database.MovieReservationDatabase
import woowacourse.movie.data.entity.Movie
import woowacourse.movie.data.entity.MovieReservation
import woowacourse.movie.data.entity.MovieTheater
import woowacourse.movie.fixtures.context
import java.io.IOException

class MovieDatabaseTest {
    private lateinit var movieReservationDao: MovieReservationDao
    private lateinit var movieDao: MovieDao

    private lateinit var theaterDao: MovieTheaterDao

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

        movieDao.insert(Movie.STUB)
        theaterDao.insertAll(MovieTheater.STUB_A, MovieTheater.STUB_B, MovieTheater.STUB_C)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertReservation() {
        movieReservationDao.insertAll(MovieReservation.STUB)

        val reservation = movieReservationDao.getAll()
        assertThat(reservation).contains(MovieReservation.STUB)
    }
}
