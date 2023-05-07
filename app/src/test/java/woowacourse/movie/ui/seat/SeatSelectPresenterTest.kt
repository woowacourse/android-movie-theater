package woowacourse.movie.ui.seat

import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.dbHelper.TicketsDbHelper
import woowacourse.movie.model.SeatSelectState

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
        verify { view.showMoneyText(any()) }
    }

    @Test
    fun `티켓을 추가한다`() {
        // when
        presenter.addTicket(emptyList())

        // then
        verify { ticketsDbHelper.insert(any()) }
        verify { view.navigateToConfirmView(any()) }
    }

    @Test
    fun `필요한 티켓 수를 반환한다`() {
        // when
        presenter.getRequireCount()

        // then
        verify { seatSelectState.countState.value }
    }
}
