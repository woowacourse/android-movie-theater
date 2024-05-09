package woowacourse.movie.database

import androidx.room.Room
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.dao.MovieReservationDao
import woowacourse.movie.data.database.MovieReservationDatabase
import woowacourse.movie.data.entity.MovieReservation
import woowacourse.movie.fixtures.context
import java.io.IOException

class MovieReservationTest {
    private lateinit var movieReservationDao: MovieReservationDao
    private lateinit var db: MovieReservationDatabase

    @Before
    fun setUp(){
        db = Room.inMemoryDatabaseBuilder(
            context, MovieReservationDatabase::class.java,
        ).build()
        movieReservationDao = db.movieReservationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    fun insertReservation(){
        movieReservationDao.insertAll(MovieReservation.STUB)

        val reservation = movieReservationDao.getAll()
        assertThat(reservation).contains(MovieReservation.STUB)
    }
}
