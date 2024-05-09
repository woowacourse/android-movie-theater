package woowacourse

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.AppDatabase
import woowacourse.movie.data.movie.MovieDao
import woowacourse.movie.data.movie.toDto
import woowacourse.movie.data.screening.ScreeningRefDao
import woowacourse.movie.data.screening.toDto
import woowacourse.movie.data.theater.TheaterDao
import woowacourse.movie.data.theater.toDto
import woowacourse.movie.model.Movie
import woowacourse.movie.model.ScreeningRef
import woowacourse.movie.model.Theater

class ScreeningDbTest {
    private lateinit var movieDao: MovieDao
    private lateinit var theaterDao: TheaterDao
    private lateinit var screeningRefDao: ScreeningRefDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        screeningRefDao = db.screeningDao()
        movieDao = db.movieDao()
        theaterDao = db.theaterDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `상영_정보를_db에_저장하고_불러옵니다`() {
        movieDao.insert(Movie.STUB_A.toDto())
        theaterDao.insert(Theater.STUB_A.toDto())
        val screeningRefDto = ScreeningRef.STUB.toDto()
        screeningRefDao.insert(screeningRefDto)
        val actual = screeningRefDao.findById(1)
        val expected = screeningRefDto
        assertThat(actual).isEqualTo(expected)
    }
}
