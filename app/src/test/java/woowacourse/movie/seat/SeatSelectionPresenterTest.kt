package woowacourse.movie.seat

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.fixture.SEOLLEUNG
import woowacourse.movie.fixture.createTicket
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.ui.model.TicketUiModel

class SeatSelectionPresenterTest {
    private lateinit var presenter: SeatSelectionPresenter
    private lateinit var mockView: SeatSelectionContract.View
    private lateinit var mockTicketUiData: TicketUiModel

    @BeforeEach
    fun setUp() {
        mockView = mockk(relaxed = true)
        mockTicketUiData = createTicket(SEOLLEUNG, listOf(), 2).toUiModel()

        presenter = SeatSelectionPresenter(mockView)
        presenter.initializeData(mockTicketUiData)
    }

    @Test
    fun `좌석을_클릭하면_좌석_상태와_버튼_활성화_상태를_갱신한다`() {
        // when
        presenter.updateSeats(0, 0)

        // then
        verify { mockView.showSeatState(SEAT_A1.toUiModel()) }
        verify { mockView.updateCanBook(any()) }
        verify { mockView.showTicket(match { it.seats == setOf(SEAT_A1.toUiModel()) && it.totalPrice == "10,000" }) }
    }

    @Test
    fun `예약버튼_클릭시_예약_다이얼로그를_표시한다`() {
        // when
        presenter.completeBooking()

        // then
        verify { mockView.showBookingAlertDialog(any()) }
    }
}
