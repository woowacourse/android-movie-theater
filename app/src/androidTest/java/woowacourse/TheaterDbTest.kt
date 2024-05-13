package woowacourse

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.Theater
import woowacourse.movie.model.data.AppDatabase
import woowacourse.movie.model.data.theater.TheaterDao
import woowacourse.movie.model.data.theater.toDto
import woowacourse.movie.model.data.theater.toTheater

class TheaterDbTest {
    private lateinit var theaterDao: TheaterDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        theaterDao = db.theaterDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `영화관_정보를_DB에_저장하고_불러온다`() {
        theaterDao.insert(Theater.STUB_A.toDto())
        val actual = theaterDao.findById(1)?.toTheater()
        val expected = Theater.STUB_A
        assertThat(actual).isEqualTo(expected)
    }
}
