package woowacourse.movie.ui.confirm

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import woowacourse.movie.model.TicketsState

class ReservationConfirmPresenterTest {
    @Test
    fun `예약 확인 화면을 초기화한다`() {
        // given
        val view = mockk<ReservationConfirmContract.View>()
        val tickets: TicketsState = mockk(relaxed = true)
        val presenter = ReservationConfirmPresenter(view, tickets)

        every { view.setTicket(any()) } returns Unit
        every { view.registerNotification(any()) } returns Unit

        // when
        presenter.getTicket()

        // then
        verify { view.setTicket(any()) }
        verify { view.registerNotification(any()) }
    }

    @Test
    fun `가격 할인 정책을 적용하면 화면 금액이 바뀐다`() {
        // given
        val view = mockk<ReservationConfirmContract.View>()
        val tickets: TicketsState = mockk(relaxed = true)
        val presenter = ReservationConfirmPresenter(view, tickets)

        every { view.setMoneyTextView(any()) } returns Unit

        // when
        presenter.discountApplyMoney(tickets)

        // then
        verify { view.setMoneyTextView(any()) }
    }
}
