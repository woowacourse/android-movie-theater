package woowacourse.movie.data.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.entity.TicketEntity
import woowacourse.movie.fixture.HARRY_POTTER
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.fixture.SEAT_A2
import woowacourse.movie.fixture.SEAT_E1
import woowacourse.movie.fixture.STAR_IS_BORN
import woowacourse.movie.fixture.createTicket
import woowacourse.movie.mapper.toEntity

class TicketDaoTest {
    private val dummyData: TicketEntity = createTicket(HARRY_POTTER, listOf(SEAT_A1, SEAT_E1), 2).toEntity().copy(id = 1)
    private lateinit var db: MovieDatabase
    private lateinit var dao: TicketDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room
                .inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        dao = db.TicketDao()
        dao.saveTicket(dummyData)
    }

    @Test
    fun `티켓_정보를_모두_불러온다`() {
        // given & when
        val actual = dao.getAllTickets()
        val expected = listOf(dummyData)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `티켓_정보를_저장한다`() {
        // given
        val ticket: TicketEntity = createTicket(STAR_IS_BORN, listOf(SEAT_A1, SEAT_A2), 2).toEntity().copy(id = 2)

        // when
        dao.saveTicket(ticket)
        val actual = dao.getAllTickets()

        // then
        assertThat(actual).contains(ticket)
    }

    @After
    fun tearDown() {
        db.close()
    }
}
