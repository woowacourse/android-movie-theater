package woowacourse.movie.data.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.entity.toEntity
import woowacourse.movie.fixture.BOOKED_TICKET

@Suppress("ktlint:standard:function-naming")
class TicketDaoTest {
    private lateinit var db: MovieDatabase
    private lateinit var ticketDao: TicketDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room
                .inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
                .build()
        ticketDao = db.ticketDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun 데이터베이스에_티켓을_저장하면_데이터가_조회된다() {
        // given
        val ticket = BOOKED_TICKET.toEntity()

        // when
        ticketDao.insert(ticket)
        val result = ticketDao.getAll()

        // then
        assertEquals(1, result.size)
        assertEquals(BOOKED_TICKET.movie.title, result[0].movie.title)
    }
}
