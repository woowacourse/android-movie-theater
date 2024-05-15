package woowacourse.movie.data.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.MovieApplication.Companion.database

@RunWith(AndroidJUnit4::class)
class ReservationHistoryDAOTest {
    @After
    fun tearDown() {
        database.reservationHistoryDao().clearReservations()
    }

    @Test
    fun saveReservationHistory() {
        val reservationHistoryEntity =
            ReservationHistoryEntity("2024-03-17", "15:00", 1, "A1", 0L, 0)
        database.reservationHistoryDao().saveReservationHistory(reservationHistoryEntity)

        val actual = database.reservationHistoryDao().findReservationHistories()

        Assertions.assertThat(actual.contains(reservationHistoryEntity)).isTrue()
    }

    @Test
    fun findReservationHistories() {
        val reservationHistoryEntity1 =
            ReservationHistoryEntity("2024-03-17", "15:00", 1, "A1", 0L, 0)
        val reservationHistoryEntity2 =
            ReservationHistoryEntity("2024-04-17", "15:00", 1, "A1", 0L, 0)
        database.reservationHistoryDao().saveReservationHistory(reservationHistoryEntity1)
        database.reservationHistoryDao().saveReservationHistory(reservationHistoryEntity2)

        val actual = database.reservationHistoryDao().findReservationHistories()

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
        database.reservationHistoryDao().saveReservationHistory(reservationHistoryEntity)

        database.reservationHistoryDao().clearReservations()

        val actual = database.reservationHistoryDao().findReservationHistories()

        Assertions.assertThat(actual.isEmpty()).isTrue()
    }
}
