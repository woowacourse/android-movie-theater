package woowacourse.movie.booking

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.booking.complete.BookingCompleteContract
import woowacourse.movie.booking.complete.BookingCompletePresenter
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.fixture.SEAT_A2
import woowacourse.movie.fixture.SEAT_C1
import woowacourse.movie.fixture.SEOLLEUNG
import woowacourse.movie.fixture.createTicket
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.Ticket
import woowacourse.movie.ui.model.TicketUiModel

class SchedulerCompletePresenterTest {
    private lateinit var presenter: BookingCompletePresenter
    private lateinit var mockView: BookingCompleteContract.View
    private lateinit var mockTicket: Ticket
    private lateinit var mockTicketUiData: TicketUiModel

    @BeforeEach
    fun setUp() {
        mockView = mockk(relaxed = true)

        val seats =
            listOf(
                SEAT_A1,
                SEAT_A2,
                SEAT_C1,
            )

        mockTicket = createTicket(SEOLLEUNG, seats)

        mockTicketUiData = mockTicket.toUiModel()

        presenter = BookingCompletePresenter(view = mockView)
    }

    @Test
    fun `영화 예매 정보를 화면에 표시할 수 있다`() {
        presenter.initializeData(mockTicketUiData)

        verify { mockView.showBookingCompleteResult(mockTicketUiData) }
        verify {
            mockView.showBookingCompleteResult(
                match {
                    it.headCount == 3 && it.selectedDateText == "2028.10.13" &&
                        it.selectedTimeText == "11:00" && it.seats == "A1, A2, C1" &&
                        it.totalPrice == "35,000"
                },
            )
        }
    }
}
