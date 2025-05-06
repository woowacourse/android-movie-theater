package woowacourse.movie.seat

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.fixture.SEOLLEUNG
import woowacourse.movie.fixture.createTicket
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.ui.model.SeatUiModel
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
        // given
        val seatSlot = slot<SeatUiModel>()
        val isSelectedSlot = slot<Boolean>()
        val canBookSlot = slot<Boolean>()
        val ticketSlot = slot<TicketUiModel>()

        every { mockView.showSeatState(capture(seatSlot), capture(isSelectedSlot)) } just Runs
        every { mockView.updateCanBook(capture(canBookSlot)) } just Runs
        every { mockView.showTicket(capture(ticketSlot)) } just Runs

        // when
        presenter.updateSeats(0, 0)

        // then
        verify {
            mockView.showSeatState(SEAT_A1.toUiModel(), true)
            mockView.updateCanBook(any())
            mockView.showTicket(any())
        }

        assertThat(seatSlot.captured).isEqualTo(SEAT_A1.toUiModel())
        assertThat(isSelectedSlot.captured).isTrue()
        assertThat(canBookSlot.captured).isFalse()
        assertThat(ticketSlot.captured.seats).isEqualTo(setOf(SEAT_A1.toUiModel()))
        assertThat(ticketSlot.captured.totalPrice).isEqualTo("10,000")
    }

    @Test
    fun `예약버튼_클릭시_예약_다이얼로그를_표시한다`() {
        // given
        val ticket = slot<TicketUiModel>()

        every { mockView.showBookingAlertDialog(capture(ticket)) } just Runs

        // when
        presenter.completeBooking()

        // then
        verify { mockView.showBookingAlertDialog(any()) }

        assertThat(ticket.captured).isEqualTo(mockTicketUiData)
    }
}
