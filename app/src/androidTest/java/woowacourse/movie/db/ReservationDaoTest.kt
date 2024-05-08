package woowacourse.movie.db

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.model.Seat

@RunWith(AndroidJUnit4::class)
class ReservationDaoTest {
    private lateinit var database: ReservationDatabase
    private lateinit var dao: ReservationDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = ReservationDatabase.getDatabase(context)
        dao = database.reservationDao()
    }

    @After
    fun tearDown() {
        dao.deleteAll()
    }

    @Test
    fun `예매_정보_저장_테스트`() {
        val data =
            ReservationEntity("영화 제목", "2024.05.09", "10:00", listOf(Seat(0, 0), Seat(0, 1)), "선릉")
        val actual = dao.saveReservation(data)
        assertThat(actual > 0).isTrue()
    }

    @Test
    fun `저장된_예매_정보_조회_테스트_비어_있는_경우`() {
        val actual = dao.getAllReservation()
        assertThat(actual).isEmpty()
    }

    @Test
    fun `저장된_예매_정보_조회_테스트_저장된_데이터가_있는_경우`() {
        val data =
            ReservationEntity("영화 제목", "2024.05.09", "10:00", listOf(Seat(0, 0), Seat(0, 1)), "선릉")
        dao.saveReservation(data)
        val actual = dao.getAllReservation()
        assertThat(actual[0].movieTitle).isEqualTo("영화 제목")
    }
}
