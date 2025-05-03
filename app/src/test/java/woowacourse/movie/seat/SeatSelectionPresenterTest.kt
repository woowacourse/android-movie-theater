package woowacourse.movie.seat

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.fixture.SEOLLEUNG
import woowacourse.movie.fixture.createTicket
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.Ticket
import woowacourse.movie.ui.model.TicketUiModel

class SeatSelectionPresenterTest {
    private lateinit var presenter: SeatSelectionPresenter
    private lateinit var mockView: SeatSelectionContract.View
    private lateinit var mockTicket: Ticket
    private lateinit var mockTicketUiData: TicketUiModel

    @BeforeEach
    fun setUp() {
        mockView = mockk(relaxed = true)

        mockTicket = createTicket(SEOLLEUNG, listOf(), 2)

        mockTicketUiData = mockTicket.toUiModel()
        presenter = SeatSelectionPresenter(mockView)
    }

    @Test
    fun `티켓을_바탕으로_View에_초기_데이터를_보여준다`() {
        presenter.initializeData(mockTicketUiData)

        verify { mockView.showTicket(mockTicketUiData) }
    }
//
//    @Test
//    fun `좌석을_클릭하면_좌석_상태와_버튼_활성화_상태를_갱신한다`() {
//        val seatTextView = mockk<TextView>(relaxed = true)
//        every { seatTextView.text.toString() } returns "A1"
//
//        presenter.initializeData(mockTicketUiData)
//        presenter.onSeatClicked(seatTextView)
//
//        verify { mockView.showSeatState(seatTextView, isSelected = true) }
//        verify { mockView.setButtonEnabled(any()) }
//        verify { mockView.showTicket(match { it.seats == "A1" && it.totalPrice == "10,000" }) }
//    }

    @Test
    fun `예약버튼_클릭시_예약_다이얼로그를_표시한다`() {
        presenter.initializeData(mockTicketUiData)

        presenter.completeBooking()

        verify { mockView.showBookingAlertDialog(any()) }
    }
}
