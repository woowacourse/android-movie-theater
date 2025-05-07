package woowacourse.movie.presentation.result

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.fixture.TICKET

class BookingResultPresenterTest {
    private lateinit var view: BookingResultContract.View
    private lateinit var presenter: BookingResultContract.Presenter
    private lateinit var ticket: Ticket

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        ticket = TICKET
        presenter = BookingResultPresenter(view, ticket)
    }

    @Test
    fun `티켓의 정보가 출력된다`() {
        // when
        presenter.loadBookingResult()

        // then
        verify { view.showTicketInfo(ticket) }
    }
}
