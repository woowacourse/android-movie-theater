package woowacourse.movie.ui.seat

import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import woowacourse.movie.dbHelper.TicketsDbHelper
import woowacourse.movie.model.SeatSelectState

class SeatSelectPresenterTest {
    @Test
    fun `선택 좌석선택 상태를 보여준다`() {
        // given
        val view = mockk<SeatSelectContract.View>(relaxed = true)
        val ticketsDbHelper = mockk<TicketsDbHelper>(relaxed = true)
        val seatSelectState = mockk<SeatSelectState>(relaxed = true)
        val cinemaName = "강남"
        val presenter = SeatSelectPresenter(view, ticketsDbHelper, seatSelectState, cinemaName)

        // when
        presenter.getSeatSelectState()

        // then
        verify { view.showSeatSelectState(seatSelectState) }
        verify { view.initSeatTable(seatSelectState) }
    }

    @Test
    fun `할인 적용 금액을 보여준다`() {
        // given
        val view = mockk<SeatSelectContract.View>(relaxed = true)
        val ticketsDbHelper = mockk<TicketsDbHelper>(relaxed = true)
        val seatSelectState = mockk<SeatSelectState>(relaxed = true)
        val cinemaName = "강남"
        val presenter = SeatSelectPresenter(view, ticketsDbHelper, seatSelectState, cinemaName)

        // when
        presenter.discountApply(emptyList())

        // then
        verify { view.setMoneyText(any()) }
    }

    @Test
    fun `티켓을 추가한다`() {
        // given
        val view = mockk<SeatSelectContract.View>(relaxed = true)
        val ticketsDbHelper = mockk<TicketsDbHelper>(relaxed = true)
        val seatSelectState = mockk<SeatSelectState>(relaxed = true)
        val cinemaName = "강남"
        val presenter = SeatSelectPresenter(view, ticketsDbHelper, seatSelectState, cinemaName)

        // when
        presenter.addTicket(emptyList())

        // then
        verify { ticketsDbHelper.insert(any()) }
        verify { view.navigateToConfirmView(any()) }
    }

    @Test
    fun `필요한 티켓 수를 반환한다`() {
        // given
        val view = mockk<SeatSelectContract.View>(relaxed = true)
        val ticketsDbHelper = mockk<TicketsDbHelper>(relaxed = true)
        val seatSelectState = mockk<SeatSelectState>(relaxed = true)
        val cinemaName = "강남"
        val presenter = SeatSelectPresenter(view, ticketsDbHelper, seatSelectState, cinemaName)

        // when
        presenter.getRequireCount()

        // then
        verify { seatSelectState.countState.value }
    }
}
