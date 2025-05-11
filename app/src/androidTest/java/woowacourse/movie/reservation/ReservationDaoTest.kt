package woowacourse.movie.reservation

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.After
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.DB_TEST_MOVIE_TICKETS
import woowacourse.movie.DB_TEST_MOVIE_TICKET_1
import woowacourse.movie.DB_TEST_MOVIE_TICKET_2
import woowacourse.movie.presenter.reservationDetails.ReservationDao
import woowacourse.movie.presenter.reservationDetails.ReservationDatabase

class ReservationDaoTest {
    private lateinit var db: ReservationDatabase
    private lateinit var dao: ReservationDao

    @BeforeEach
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db =
            Room
                .inMemoryDatabaseBuilder(context, ReservationDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        dao = db.reservationDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `영화_예매_정보를_저장한_후_해당_정보를_올바르게_조회한다`() {
        // given

        // when
        dao.saveReservation(DB_TEST_MOVIE_TICKET_1)
        val result = dao.findReservation(1L)

        // then
        result shouldNotBe null
        result?.ticketId shouldBe 1L
        result shouldBe DB_TEST_MOVIE_TICKET_1
    }

    @Test
    fun `예매한_모든_영화_정보를_조회한다`() {
        // given
        dao.saveReservation(DB_TEST_MOVIE_TICKET_1)
        dao.saveReservation(DB_TEST_MOVIE_TICKET_2)

        // when
        val result = dao.findReservations()

        // then
        result shouldBe DB_TEST_MOVIE_TICKETS
    }

    @Test
    fun `예매_정보를_모두_제거한다`() {
        // given
        dao.saveReservation(DB_TEST_MOVIE_TICKET_1)
        dao.saveReservation(DB_TEST_MOVIE_TICKET_2)
        dao.clear()
        // when
        val result = dao.findReservations()

        // then
        result shouldBe emptyList()
    }
}
