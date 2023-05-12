package woowacourse.movie.ui.seat

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.dbHelper.TicketsDbHelper
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.SeatSelectState
import woowacourse.movie.model.TicketsState

class SeatSelectPresenterTest {
    private lateinit var view: SeatSelectContract.View
    private lateinit var ticketsDbHelper: TicketsDbHelper
    private lateinit var seatSelectState: SeatSelectState
    private lateinit var cinemaName: String
    private lateinit var presenter: SeatSelectPresenter

    @Before
    fun setUp() {
        // given
        view = mockk(relaxed = true)
        ticketsDbHelper = mockk(relaxed = true)
        seatSelectState = mockk(relaxed = true)
        cinemaName = "CGV"
        presenter = SeatSelectPresenter(view, ticketsDbHelper, seatSelectState, cinemaName)
    }

    @Test
    fun `선택 좌석선택 상태를 보여준다`() {
        // when
        presenter.setUpSeatSelectState()

        // then
        verify { view.showReservationTitle(seatSelectState) }
        verify { view.initSeatTable(seatSelectState) }
    }

    @Test
    fun `할인 적용 금액을 보여준다`() {
        // when
        presenter.discountMoneyApply(emptyList())

        // then
        verify { view.setConfirmClickable(true) }
        verify { view.showMoneyText(any()) }
    }

    @Test
    fun `티켓을 추가한다`() {
        // given
        val seatPositionState = getSeatPositionState()
        val slot = slot<TicketsState>()
        every {
            ticketsDbHelper.insert(capture(slot))
        } answers { println("slot : ${slot.captured}") }

        every {
            view.navigateToConfirmView(capture(slot))
        } answers {
            println("slot : ${slot.captured}")
        }

        // when
        presenter.addTicket(seatPositionState)

        // then
        verify { ticketsDbHelper.insert(any()) }
        verify { view.navigateToConfirmView(any()) }
    }

    private fun getSeatPositionState(): List<SeatPositionState> {
        return listOf(
            SeatPositionState(1, 1),
            SeatPositionState(1, 2),
            SeatPositionState(1, 3)
        )
    }
}
