package woowacourse.movie

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.movie.data.Reservation
import woowacourse.movie.data.ReservationDao
import woowacourse.movie.data.ReservationDatabase
import woowacourse.movie.fixture.HARRY_POTTER
import woowacourse.movie.fixture.SEOLLEUNG
import woowacourse.movie.seat.SeatSelectionActivity

class ReservationDaoTest {
    private lateinit var reservationDao: ReservationDao
    private lateinit var reservationDatabase: ReservationDatabase

    @get:Rule
    val activityRule = ActivityScenarioRule(SeatSelectionActivity::class.java)

    @Before
    fun setup() {
        val context: Context = ApplicationProvider.getApplicationContext<Context>()
        reservationDatabase =
            Room.inMemoryDatabaseBuilder(context, ReservationDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        reservationDao = reservationDatabase.reservationDao()
    }

    @Test
    fun `데이터베이스에_movieTicket_데이터를_넣을_수_있다`() {
        setup()
        val reservation =
            Reservation(
                uid = 1,
                theater = SEOLLEUNG,
                title = HARRY_POTTER,
                headCount = 2,
                selectedDate = "2024.05.10",
                selectedTime = "17:00",
                seats = "A1,A2",
                price = "20,000",
            )

        reservationDao.insertReservation(reservation)
        val expected = reservationDao.getTicketByUid(1)

        assertThat(expected).isEqualTo(reservation)
    }

    @After
    fun tearDown() {
        reservationDatabase.close()
    }
}
