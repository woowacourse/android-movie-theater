package woowacourse.movie.data.db

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import woowacourse.movie.fixture.ticketEntity1
import woowacourse.movie.fixture.ticketEntity2
import woowacourse.movie.fixture.fakeContext

@RunWith(AndroidJUnit4::class)
class TicketDaoTest {

    private lateinit var db: UserDatabase
    private lateinit var ticketDao: TicketDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(fakeContext, UserDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        ticketDao = db.ticketDao()
    }

    @Test
    fun `새로운_예매_정보_추가하면_아이디를_반환한다`() {
        //when
        val bookings = listOf(ticketEntity1, ticketEntity2)

        // given
        bookings.forEach { ticketDao.insert(it) }
        val expected = ticketDao.readAll()

        // then
        assertEquals(
            expected,
            listOf(ticketEntity1.copy(id = 1), ticketEntity2.copy(id = 2))
        )
    }

    @Test
    fun `새로운_예매_정보_추가하고_모든_예매_정보를_가져온다`() {
        //when
        val bookings = listOf(ticketEntity1, ticketEntity2)

        // given
        bookings.forEach { ticketDao.insert(it) }
        val expected = ticketDao.readAll()

        // then
        assertEquals(
            expected,
            listOf(ticketEntity1.copy(id = 1), ticketEntity2.copy(id = 2))
        )
    }

    @Test
    fun `특정_ID의_예매_정보를_불러온다`() {
        //when
        val bookings = listOf(ticketEntity1, ticketEntity2)

        // given
        bookings.forEach { ticketDao.insert(it) }
        val expected = ticketDao.readById(1L)

        // then
        assertEquals(expected, ticketEntity1.copy(id = 1))
    }

    @After
    fun tearDown() {
        db.close()
    }
}
