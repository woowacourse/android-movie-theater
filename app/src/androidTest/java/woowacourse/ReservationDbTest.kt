package woowacourse

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.Movie
import woowacourse.movie.model.ReservationRef
import woowacourse.movie.model.ScreeningRef
import woowacourse.movie.model.Theater
import woowacourse.movie.model.data.AppDatabase
import woowacourse.movie.model.data.movie.MovieDao
import woowacourse.movie.model.data.movie.toDto
import woowacourse.movie.model.data.reservationref.ReservationRefDao
import woowacourse.movie.model.data.reservationref.toDto
import woowacourse.movie.model.data.reservationref.toReservationRef
import woowacourse.movie.model.data.screeningref.ScreeningRefDao
import woowacourse.movie.model.data.screeningref.toDto
import woowacourse.movie.model.data.theater.TheaterDao
import woowacourse.movie.model.data.theater.toDto

class ReservationDbTest {
    private lateinit var movieDao: MovieDao
    private lateinit var theaterDao: TheaterDao
    private lateinit var screeningRefDao: ScreeningRefDao
    private lateinit var reservationRefDao: ReservationRefDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        movieDao = db.movieDao()
        theaterDao = db.theaterDao()
        screeningRefDao = db.screeningDao()
        reservationRefDao = db.reservationDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `영화_예매_데이터를_저장하고_불러올_수_있다`() {
        movieDao.insert(Movie.STUB_A.toDto())
        theaterDao.insert(Theater.STUB_A.toDto())
        val screeningRefDto = ScreeningRef.STUB.toDto()
        screeningRefDao.insert(screeningRefDto)

        val reservationRef = ReservationRef.STUB
        reservationRefDao.insert(reservationRef.toDto())
        val actual = reservationRefDao.findById(1)?.toReservationRef()

        val expected = reservationRef
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `모든_예매_데이터를_불러온다`() {
        movieDao.insert(Movie.STUB_A.toDto())
        theaterDao.insert(Theater.STUB_A.toDto())
        val screeningRefDto = ScreeningRef.STUB.toDto()
        screeningRefDao.insert(screeningRefDto)

        val reservationRef = ReservationRef.STUB
        reservationRefDao.insert(reservationRef.toDto())
        val actual = reservationRefDao.findAll().map { it.toReservationRef() }

        val expected = listOf(reservationRef)
        assertThat(actual).isEqualTo(expected)
    }
}
