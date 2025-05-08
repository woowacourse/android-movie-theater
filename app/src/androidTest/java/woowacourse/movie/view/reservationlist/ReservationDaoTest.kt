package woowacourse.movie.view.reservationlist

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.db.ReservationDao
import woowacourse.movie.data.db.ReservationDatabase
import woowacourse.movie.data.db.ReservationEntity
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import java.time.LocalDateTime

class ReservationDaoTest {
    private val dummyReservationEntity =
        ReservationEntity(
            movieTitle = "라라랜드",
            dateTime = LocalDateTime.now(),
            ticketCount = 1,
            seats = Seats.of(listOf(Seat("A1"))),
            totalPrice = 10000,
            theaterName = "선릉",
        )

    private val dummyReservationEntity2 =
        ReservationEntity(
            movieTitle = "승부",
            dateTime = LocalDateTime.now(),
            ticketCount = 3,
            seats = Seats.of(listOf(Seat("A1"), Seat("C2"), Seat("E2"))),
            totalPrice = 36000,
            theaterName = "잠실",
        )
    private lateinit var db: ReservationDatabase
    private lateinit var dao: ReservationDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room
                .inMemoryDatabaseBuilder(context, ReservationDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        dao = db.reservationDao()
        dao.saveReservation(dummyReservationEntity)
    }

    @Test
    fun `예약_정보를_모두_불러온다`() {
        // when
        val result = dao.getAllReservation()

        // then
        assertThat(result).hasSize(1)
        assertThat(result[0].movieTitle).isEqualTo("라라랜드")
    }

    @Test
    fun `예약_정보를_저장한다`() {
        // when
        dao.saveReservation(dummyReservationEntity2)
        val result = dao.getAllReservation()

        // then
        assertThat(result).hasSize(2)
    }

    @After
    fun tearDown() {
        db.close()
    }
}
