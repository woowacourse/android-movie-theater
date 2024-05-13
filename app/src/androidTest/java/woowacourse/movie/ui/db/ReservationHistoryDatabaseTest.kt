package woowacourse.movie.ui.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.db.reservationhistory.ReservationHistory
import woowacourse.movie.db.reservationhistory.ReservationHistoryDao
import woowacourse.movie.db.reservationhistory.ReservationHistoryDatabase
import woowacourse.movie.domain.model.Reservation
import java.io.IOException
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class ReservationHistoryDatabaseTest {
    private lateinit var reservationHistoryDao: ReservationHistoryDao
    private lateinit var reservationHistoryDatabase: ReservationHistoryDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        reservationHistoryDatabase =
            Room.inMemoryDatabaseBuilder(
                context, ReservationHistoryDatabase::class.java,
            ).build()
        reservationHistoryDao = reservationHistoryDatabase.reservationHistoryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        reservationHistoryDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun `처음으로_예매_내역을_저장하면_id_가_1인_예매_내역이_저장된다`() {
        val reservationHistory =
            ReservationHistory(
                1L,
                Reservation.NULL,
                "선릉 극장",
                LocalDate.of(2024, 5, 10),
                LocalTime.of(10, 0),
            )
        reservationHistoryDao.upsert(reservationHistory)
        val firstReservationHistory = reservationHistoryDao.getById(1)
        assertThat(firstReservationHistory.id, equalTo(reservationHistory.id))
        assertThat(firstReservationHistory.theaterName, equalTo(reservationHistory.theaterName))
    }
}
