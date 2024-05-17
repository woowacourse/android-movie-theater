package woowacourse.movie.data.db

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReservationTicketDaoTest {
    private lateinit var ticketDao: ReservationTicketDao
    private lateinit var db: ReservationTicketDatabase

    @Before
    fun createDb() {
        db =
            Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().context,
                ReservationTicketDatabase::class.java,
            ).build()
        ticketDao = db.reservationDao()
    }

    @After
    fun closeDb() {
        ticketDao.clearAllReservation()
        db.close()
    }

    @Test
    fun `데이터베이스에_값을_저장한다`() {
        // given
        val ticket = ticket()

        // when
        val id = ticketDao.saveReservationTicket(ticket)

        // then
        assertEquals(1, id)
    }

    @Test
    fun `데이터베이스에_저장된_값을_모두_불러올_수_있다`() {
        // given
        val ticket1 = ticket(ticketId = 1, movieTitle = "헝거게임")
        val ticket2 = ticket(ticketId = 2, movieTitle = "타이타닉")
        val ticket3 = ticket(ticketId = 3, movieTitle = "범죄도시")
        ticketDao.saveReservationTicket(ticket1)
        ticketDao.saveReservationTicket(ticket2)
        ticketDao.saveReservationTicket(ticket3)

        // when
        val tickets = ticketDao.findAllReservation()

        // then
        assertEquals(3, tickets.size)
        assertEquals("헝거게임", tickets[0].movieTitle)
        assertEquals("타이타닉", tickets[1].movieTitle)
        assertEquals("범죄도시", tickets[2].movieTitle)
    }

    @Test
    fun `데이터베이스에_저장된_값을_모두_지울_수_있다`() {
        // given
        val ticket1 = ticket(ticketId = 1, movieTitle = "헝거게임")
        val ticket2 = ticket(ticketId = 2, movieTitle = "타이타닉")
        ticketDao.saveReservationTicket(ticket1)
        ticketDao.saveReservationTicket(ticket2)
        val tickets = ticketDao.findAllReservation()
        assertEquals(2, tickets.size)

        // when
        ticketDao.clearAllReservation()
        val removeTicket = ticketDao.findAllReservation()

        // then
        assertEquals(0, removeTicket.size)
    }

    @Test
    fun `잘못된_id값으로_데이터베이스를_조회하면_null이다`() {
        // given
        val ticket1 = ticket(ticketId = 1, movieTitle = "헝거게임")
        val ticket2 = ticket(ticketId = 2, movieTitle = "타이타닉")
        ticketDao.saveReservationTicket(ticket1)
        ticketDao.saveReservationTicket(ticket2)

        // when
        val ticket = ticketDao.findReservationById(3)

        // then
        assertEquals(null, ticket)
    }

    @Test
    fun `id값으로_데이터베이스에_저장된_값을_찾을_수_있다`() {
        // given
        val ticket1 = ticket(ticketId = 1, movieTitle = "헝거게임")
        val ticket2 = ticket(ticketId = 2, movieTitle = "타이타닉")
        ticketDao.saveReservationTicket(ticket1)
        ticketDao.saveReservationTicket(ticket2)

        // when
        val ticket = ticketDao.findReservationById(2)

        // then
        assertEquals("타이타닉", ticket?.movieTitle)
    }
}
