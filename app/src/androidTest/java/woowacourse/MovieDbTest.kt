package woowacourse

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.Movie
import woowacourse.movie.model.data.AppDatabase
import woowacourse.movie.model.data.movie.MovieDao
import woowacourse.movie.model.data.movie.toDto
import woowacourse.movie.model.data.movie.toMovie

class MovieDbTest {
    private lateinit var movieDao: MovieDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        movieDao = db.movieDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `영화를_db에_읽고_쓰는_테스트`() {
        movieDao.insert(Movie.STUB_A.toDto())
        val actual = movieDao.getById(1)?.toMovie()
        val expected = Movie.STUB_A
        assertThat(actual).isEqualTo(expected)
    }
}
