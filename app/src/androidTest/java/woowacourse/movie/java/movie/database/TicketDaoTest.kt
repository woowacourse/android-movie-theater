package woowacourse.movie.java.movie.database

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.database.TicketDatabase
import woowacourse.movie.ticket.model.TicketEntity

@RunWith(AndroidJUnit4::class)
class TicketDaoTest {
    private val ticketDb = TicketDatabase.getDatabase(ApplicationProvider.getApplicationContext())

    @Before
    fun setUp() {
        ticketDb.ticketDao().deleteAll()
    }

    @Test
    fun `최초에_예매_내역이_비어있어야_한다`() {
        // when
        val actual = ticketDb.ticketDao().getAll()
        // then
        assertThat(actual).isEmpty()
    }

    @Test
    fun `티켓이_만들어지면_예매_내역에_저장되어야_한다`() {
        // given
        val ticket = TicketEntity(
            1,
            "반지의 제왕",
            "2024.05.13",
            "15:40",
            3,
            "A1, B3",
            "알송 극장",
            10,
        )
        // when
        ticketDb.ticketDao().insertAll(ticket)
        val actual = ticketDb.ticketDao().getAll().first()
        // then
        assertThat(actual).isEqualTo(ticket)
    }
}
