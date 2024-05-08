package woowacourse.movie.data.db

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReservationHistoryDAOTest {
    private lateinit var database: ReservationHistoryDatabase
    private lateinit var reservationHistoryDAO: ReservationHistoryDAO

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database =
            ReservationHistoryDatabase.getInstance(context)
        reservationHistoryDAO = database.reservationHistoryDao()
    }

    @After
    fun tearDown() {
        reservationHistoryDAO.clearReservations()
    }

    @Test
    fun saveReservationHistory() {
        val reservationHistoryEntity =
            ReservationHistoryEntity("2024-03-17", "15:00", 1, "A1", 0L, 0)
        reservationHistoryDAO.saveReservationHistory(reservationHistoryEntity)

        val actual = reservationHistoryDAO.findReservationHistories()

        Assertions.assertThat(actual.contains(reservationHistoryEntity)).isTrue()
    }

    @Test
    fun findReservationHistories() {
        val reservationHistoryEntity1 =
            ReservationHistoryEntity("2024-03-17", "15:00", 1, "A1", 0L, 0)
        val reservationHistoryEntity2 =
            ReservationHistoryEntity("2024-04-17", "15:00", 1, "A1", 0L, 0)
        reservationHistoryDAO.saveReservationHistory(reservationHistoryEntity1)
        reservationHistoryDAO.saveReservationHistory(reservationHistoryEntity2)

        val actual = reservationHistoryDAO.findReservationHistories()

        Assertions.assertThat(
            actual.containsAll(
                listOf(
                    reservationHistoryEntity1,
                    reservationHistoryEntity2,
                ),
            ),
        ).isTrue()
    }

    @Test
    fun clearReservations() {
        val reservationHistoryEntity =
            ReservationHistoryEntity("2024-03-17", "15:00", 1, "A1", 0L, 0)
        reservationHistoryDAO.saveReservationHistory(reservationHistoryEntity)

        reservationHistoryDAO.clearReservations()

        val actual = reservationHistoryDAO.findReservationHistories()

        Assertions.assertThat(actual.isEmpty()).isTrue()
    }
}
