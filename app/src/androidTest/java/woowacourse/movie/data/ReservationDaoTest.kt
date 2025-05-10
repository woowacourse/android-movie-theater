package woowacourse.movie.data

import androidx.room.Room
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.db.ReservationDao
import woowacourse.movie.data.db.ReservationDatabase
import woowacourse.movie.data.db.ReservationEntity
import woowacourse.movie.domain.model.cinema.Seat
import woowacourse.movie.presentation.fixture.fakeContext
import java.time.LocalDateTime

class ReservationDaoTest {
    private lateinit var reservationDao: ReservationDao
    private val fakeReservation =
        ReservationEntity(
            "해리 포터",
            "우아한 극장",
            LocalDateTime.now(),
            listOf(Seat(1, 1)),
            1,
            10000,
            "1",
        )

    @Before
    fun setUp() {
        val database =
            Room
                .inMemoryDatabaseBuilder(fakeContext, ReservationDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        reservationDao = database.reservationDao()
        reservationDao.insert(fakeReservation.copy(id = "2"), fakeReservation.copy(id = "3"))
    }

    @Test
    fun `데이터베이스에_예매_내역을_추가한다`() {
        // when
        reservationDao.insert(fakeReservation)
        val result = reservationDao.getAll()

        // then
        assertThat(result).contains(fakeReservation)
    }

    @Test
    fun `데이터베이스에_존재하는_모든_예매_내역을_조회한다`() {
        // when
        val result = reservationDao.getAll()

        // then
        assertThat(result).containsExactly(
            fakeReservation.copy(id = "2"),
            fakeReservation.copy(id = "3"),
        )
    }

    @Test
    fun `중복된_예매_내역은_추가되지_않는다`() {
        // given
        val dummyReservation = fakeReservation.copy(id = "10")

        // when
        reservationDao.insert(dummyReservation)
        reservationDao.insert(dummyReservation)

        val histories = reservationDao.getAll()
        val result = histories.count { it == dummyReservation }

        // then
        assertThat(result).isEqualTo(1)
    }
}
