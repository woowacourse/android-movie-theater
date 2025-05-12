package woowacourse.movie.presentation.ticket

import android.content.Context
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.fixture.TICKET
import woowacourse.movie.fixture.repository.FakeTicketRepository
import woowacourse.movie.presentation.ticket.list.TicketListContract
import woowacourse.movie.presentation.ticket.list.TicketListPresenter

class TicketListPresenterTest {
    private lateinit var view: TicketListContract.View
    private lateinit var presenter: TicketListContract.Presenter

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        val context: Context = mockk(relaxed = true)
        val ticketRepository = FakeTicketRepository()
        presenter = TicketListPresenter(view, context, ticketRepository)
    }

    @Test
    fun `티켓 목록을 화면에 출력한다`() {
        // when
        presenter.loadTicketList()

        // then
        verify { view.showTicketList(any()) }
    }

    @Test
    fun `티켓 정보를 선택하면 예매 정보 화면으로 이동한다`() {
        // when
        presenter.selectTicket(TICKET)

        // then
        verify { view.navigateToTicketDetail(TICKET) }
    }
}
