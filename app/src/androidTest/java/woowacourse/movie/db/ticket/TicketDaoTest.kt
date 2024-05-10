package woowacourse.movie.db.ticket

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.model.seats.Seats
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class TicketDaoTest {
    private val ticketDb = Room.databaseBuilder(getApplicationContext(), TicketDatabase::class.java, "reservation_history")
    private lateinit var ticketDao: TicketDao

    @Before
    fun setUp() {
        ticketDao = ticketDb.build().ticketDao()
    }

    @Test
    fun `티켓을_저장한다`() {
        val actual =
            ticketDao.insert(
                Ticket(
                    screeningDateTime = LocalDateTime.of(LocalDate.of(2024, 5, 9), LocalTime.of(10, 0)),
                    movieTitle = "해리 포터와 마법사의 돌",
                    theaterName = "선릉 극장",
                    seats = Seats(),
                ),
            )

        assertThat(actual).isGreaterThan(0)
    }
}
