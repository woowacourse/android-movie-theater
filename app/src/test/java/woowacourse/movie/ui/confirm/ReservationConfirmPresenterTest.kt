package woowacourse.movie.ui.confirm

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import java.time.LocalDate
import java.time.LocalDateTime
import org.junit.Assert.assertEquals
import org.junit.Test
import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.TicketsState

class ReservationConfirmPresenterTest {
    @Test
    fun `가격 할인 정책을 적용하면 화면 금액이 바뀐다`() {
        // given
        val view = mockk<ReservationConfirmContract.View>(relaxed = true)
        val presenter = ReservationConfirmPresenter(view)
        val slot = slot<MoneyState>()
        val tickets = TicketsState(
            MovieState(
                1,
                "title",
                LocalDate.MIN,
                LocalDate.MIN.plusDays(3),
                152,
                "description"
            ),
            LocalDateTime.MIN,
            listOf(SeatPositionState(1, 1))
        )
        every {
            view.setMoneyTextView(capture(slot))
        } answers { println("slot = ${slot.captured}") }

        // when
        presenter.setDiscountApplyMoney(tickets)

        // then
        assertEquals(slot.captured, MoneyState(8000))
        verify { view.setMoneyTextView(slot.captured) }
    }
}
