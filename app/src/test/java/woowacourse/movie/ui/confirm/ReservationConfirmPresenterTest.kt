package woowacourse.movie.ui.confirm

import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.TicketsState

class ReservationConfirmPresenterTest {
    private lateinit var view: ReservationConfirmContract.View
    private lateinit var tickets: TicketsState
    private lateinit var presenter: ReservationConfirmPresenter

    @Before
    fun setUp() {
        // given
        view = mockk(relaxed = true)
        tickets = mockk(relaxed = true)
        presenter = ReservationConfirmPresenter(view, tickets)
    }

    @Test
    fun `예약 확인 화면을 초기화한다`() {
        // when
        presenter.setUpTicket()

        // then
        verify { view.showTicket(any()) }
        verify { view.registerNotification(any()) }
    }

    @Test
    fun `가격 할인 정책을 적용하면 화면 금액이 바뀐다`() {
        // when
        presenter.discountApplyMoney(tickets)

        // then
        verify { view.showMoney(any()) }
    }
}
