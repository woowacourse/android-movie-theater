package woowacourse.movie.ticket

import android.content.Context
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.dao.TicketDao
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.fixture.GANGNAM
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.fixture.SEAT_E1
import woowacourse.movie.fixture.SEOLLEUNG
import woowacourse.movie.fixture.createTicket
import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toEntity
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.ui.model.TicketUiModel

class TicketListPresenterTest {
    private lateinit var presenter: TicketListPresenter
    private lateinit var mockView: TicketListContract.View
    private lateinit var mockContext: Context
    private lateinit var mockDatabase: MovieDatabase
    private lateinit var mockTicketDao: TicketDao

    private val dummyTickets =
        listOf(
            createTicket(SEOLLEUNG, listOf(SEAT_A1)),
            createTicket(GANGNAM, listOf(SEAT_E1)),
        ).map { it.toEntity() }

    @BeforeEach
    fun setUp() {
        mockView = mockk(relaxed = true)
        mockContext = mockk()
        mockDatabase = mockk()
        mockTicketDao = mockk()

        mockkObject(MovieDatabase)
        every { MovieDatabase.getDatabase(mockContext) } returns mockDatabase
        every { mockDatabase.TicketDao() } returns mockTicketDao
        every { mockTicketDao.getAllTickets() } returns dummyTickets

        presenter = TicketListPresenter(mockView, mockDatabase)
    }

    @Test
    fun `DB에 저장된 티켓들을 가져와 UI에 표시한다`() {
        // given
        val ticket = slot<List<TicketUiModel>>()
        every { mockView.setUpReservationList(capture(ticket)) } just Runs

        // when
        presenter.initializeData()

        // then
        verify {
            mockView.setUpReservationList(any())
        }

        assertThat(ticket.captured).isEqualTo(dummyTickets.map { it.toDomain().toUiModel() })
    }
}
