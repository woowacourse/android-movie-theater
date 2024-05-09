package woowacourse.movie.database

import androidx.room.Room
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.dao.MovieTheaterDao
import woowacourse.movie.data.database.MovieTheaterDatabase
import woowacourse.movie.data.entity.MovieTheater
import woowacourse.movie.fixtures.context
import java.io.IOException

class MovieTheaterTest {
    private lateinit var theaterDao: MovieTheaterDao
    private lateinit var db: MovieTheaterDatabase

    @Before
    fun setUp() {
        db =
            Room.inMemoryDatabaseBuilder(
                context, MovieTheaterDatabase::class.java,
            ).build()
        theaterDao = db.movieTheaterDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertTheater() {
        theaterDao.insertAll(MovieTheater.STUB_A, MovieTheater.STUB_B, MovieTheater.STUB_C)

        val theaters = theaterDao.getAll()
        assertThat(theaters).contains(MovieTheater.STUB_A, MovieTheater.STUB_B, MovieTheater.STUB_C)
    }
}
