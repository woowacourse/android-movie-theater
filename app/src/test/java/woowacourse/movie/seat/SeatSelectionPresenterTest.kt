package woowacourse.movie.seat

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.fixture.SEAT_A1_NOT_SELECTED
import woowacourse.movie.fixture.SEAT_A2
import woowacourse.movie.fixture.SEAT_C1
import woowacourse.movie.fixture.SEOLLEUNG
import woowacourse.movie.fixture.createTicket
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.Ticket
import woowacourse.movie.ui.model.TicketUiModel
import kotlin.test.assertEquals

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

    @Test
    fun `좌석을 클릭하면 좌석 상태와 버튼 활성화 상태를 갱신한다`() {
        val seat = SEAT_A1_NOT_SELECTED

        every { mockView.showSeatState(seat, true) } just Runs
        every { mockView.setButtonEnabled(true) } just Runs
        every { mockView.showTicket(any()) } just Runs

        presenter.initializeData(mockTicketUiData)
        presenter.onSeatClicked(seat)

        verify { mockView.showSeatState(seat, true) }
        verify {
            mockView.showTicket(
                match { it.seats == "A1" && it.totalPrice == "10,000" },
            )
        }
    }

    @Test
    fun `선택된 좌석 등급에 따라 최종 가격이 계산된다`() {
        val captured = mutableListOf<TicketUiModel>()
        every { mockView.showTicket(capture(captured)) } just Runs

        val ticket = createTicket(SEOLLEUNG, listOf(), 2)
        presenter.initializeData(ticket.toUiModel())

        presenter.onSeatClicked(SEAT_A1)
        presenter.onSeatClicked(SEAT_C1)

        assertEquals("10,000", captured[1].totalPrice)
        assertEquals("25,000", captured[2].totalPrice)
    }

    @Test
    fun `선택된 좌석은 선택된 순서대로 정렬되어야 한다`() {
        val captured = slot<TicketUiModel>()
        every { mockView.showTicket(capture(captured)) } just Runs

        val ticket = createTicket(SEOLLEUNG, listOf(), 3)
        presenter.initializeData(ticket.toUiModel())

        presenter.onSeatClicked(SEAT_C1)
        presenter.onSeatClicked(SEAT_A2)
        presenter.onSeatClicked(SEAT_A1)

        val sortedSeats = captured.captured.seats
        assertEquals("C1, A2, A1", sortedSeats)
    }

    @Test
    fun `예약버튼_클릭시_예약_다이얼로그를_표시한다`() {
        presenter.initializeData(mockTicketUiData)

        presenter.onButtonClicked()

        verify { mockView.showBookingAlertDialog(any()) }
    }
}
