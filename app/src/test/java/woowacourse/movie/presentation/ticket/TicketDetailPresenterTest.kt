package woowacourse.movie.presentation.ticket

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.fixture.TICKET
import woowacourse.movie.presentation.ticket.detail.TicketDetailContract
import woowacourse.movie.presentation.ticket.detail.TicketDetailPresenter

class TicketDetailPresenterTest {
    private lateinit var view: TicketDetailContract.View
    private lateinit var presenter: TicketDetailContract.Presenter
    private lateinit var ticket: Ticket

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        ticket = TICKET
        presenter = TicketDetailPresenter(view, ticket)
    }

    @Test
    fun `티켓의 정보가 출력된다`() {
        // when
        presenter.loadBookingResult()

        // then
        verify { view.showTicketInfo(ticket) }
    }
}
