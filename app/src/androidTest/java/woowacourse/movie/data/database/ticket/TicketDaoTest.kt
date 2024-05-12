package woowacourse.movie.data.database.ticket

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.data.database.MovieDatabase
import kotlin.concurrent.thread

@RunWith(AndroidJUnit4::class)
@SmallTest
class TicketDaoTest {
    private lateinit var database: MovieDatabase
    private lateinit var dao: TicketDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.ticketDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun 티켓_정보를_삽입할_수_있다() {
        val insertedTicketId = dao.save(ticket1)
        assertEquals(1L, insertedTicketId)
    }

    @Test
    fun 단일_아이디_티켓_정보를_가져올_수_있다() {
        // when
        dao.save(ticket1)

        // then
        val exptectedTicket = dao.find(1)
        assertEquals(
            TicketEntity(
                id = 1,
                title = "해리포터",
                theater = "강남",
                screeningStartDateTime = "2024-03-02 13:00",
                reservationCount = 1,
                seats = "A1",
                price = 10000
            ), exptectedTicket
        )
    }

    @Test
    fun 여러_아이디_티켓_정보를_가져올_수_있다() {
        // when
        dao.save(ticket1)
        dao.save(ticket2)

        // then
        val actualTickets = dao.findAll()
        val expectedTickets = listOf(
            TicketEntity(
                id = 1,
                title = "해리포터",
                theater = "강남",
                screeningStartDateTime = "2024-03-02 13:00",
                reservationCount = 1,
                seats = "A1",
                price = 10000
            ),
            TicketEntity(
                id = 2,
                title = "해리포터2",
                theater = "강남",
                screeningStartDateTime = "2024-03-02 13:01",
                reservationCount = 1,
                seats = "A2",
                price = 10000
            )
        )
        assertEquals(expectedTickets, actualTickets)
    }

    @Test
    fun 티켓_정보를_모두_삭제할_수_있다() {
        // when
        dao.save(ticket1)
        dao.save(ticket2)
        dao.deleteAll()

        //then
        val expectedResult = emptyList<TicketEntity>()
        val actualResult = dao.findAll()
        assertEquals(expectedResult, actualResult)
    }
}

private val ticket1 = TicketEntity(
    id = 1,
    title = "해리포터",
    theater = "강남",
    screeningStartDateTime = "2024-03-02 13:00",
    reservationCount = 1,
    seats = "A1",
    price = 10000
)

private val ticket2 = TicketEntity(
    id = 2,
    title = "해리포터2",
    theater = "강남",
    screeningStartDateTime = "2024-03-02 13:01",
    reservationCount = 1,
    seats = "A2",
    price = 10000
)
