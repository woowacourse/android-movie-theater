package woowacourse.movie.db.ticket

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.model.seats.Seats
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class TicketDaoTest {
    private lateinit var db: TicketDatabase
    private lateinit var dao: TicketDao
    private val firstMockTicket: Ticket =
        Ticket(
            screeningDateTime = LocalDateTime.of(LocalDate.of(2024, 5, 9), LocalTime.of(10, 0)),
            movieTitle = "해리 포터와 마법사의 돌",
            theaterName = "선릉 극장",
            seats = Seats(),
            1,
        )
    private val secondMockTicket: Ticket =
        Ticket(
            screeningDateTime = LocalDateTime.of(LocalDate.of(2024, 3, 5), LocalTime.of(14, 0)),
            movieTitle = "해리 포터와 비밀의 방",
            theaterName = "잠실 극장",
            seats = Seats(),
            2,
        )

    @Before
    fun createDb() {
        db =
            Room.inMemoryDatabaseBuilder(
                getApplicationContext(),
                TicketDatabase::class.java,
            ).build()
        dao = db.ticketDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun `티켓을_저장한다`() {
        val actual = dao.insert(firstMockTicket)
        assertThat(actual).isGreaterThan(0)
    }

    @Test
    fun `특정_id의_티켓을_가져온다`() {
        dao.insert(firstMockTicket)
        val actualTicket = dao.find(1)
        assertThat(actualTicket.uid).isEqualTo(1)
    }

    @Test
    fun `예약된_모든_티켓을_가져온다`() {
        dao.insert(firstMockTicket)
        dao.insert(secondMockTicket)
        val actual = dao.findAll()
        assertThat(actual.size).isEqualTo(2)
    }
}
