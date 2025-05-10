package woowacourse.movie.presenter

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.FakeTicketDataSource
import woowacourse.movie.domain.fixture.ticketFixture
import woowacourse.movie.domain.model.datasource.TicketDataSource
import woowacourse.movie.view.complete.BookingCompleteContract
import woowacourse.movie.view.complete.BookingCompletePresenter

class BookingCompletePresenterTest {
    private lateinit var presenter: BookingCompletePresenter
    private lateinit var view: BookingCompleteContract.View
    private lateinit var dataSource: TicketDataSource

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        dataSource = FakeTicketDataSource()
        presenter = BookingCompletePresenter(view, dataSource)
    }

    @Test
    fun `티켓의_id를_전달하면_저장된_티켓의_정보를_보여주고_알람을_요청한다`() {
        presenter.loadTicket(ticketFixture.id, true)

        verify { view.showTicket(ticketFixture) }
        verify { view.generateAlarm(ticketFixture) }
    }
}
