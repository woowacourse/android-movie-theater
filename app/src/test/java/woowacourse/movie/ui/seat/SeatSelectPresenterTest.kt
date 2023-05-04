package woowacourse.movie.ui.seat

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import org.junit.Assert.assertEquals
import org.junit.Test
import woowacourse.movie.model.MoneyState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.TicketsState

class SeatSelectPresenterTest {
    @Test
    fun `가격 할인 정책을 적용하면 화면 금액이 바뀐다`() {
        // given
        val view = mockk<SeatSelectContract.View>(relaxed = true)
        val presenter = SeatSelectPresenter(view)
        val slot = slot<MoneyState>()
        val tickets = sampleTicketsState
        every { view.showMoneyText(capture(slot)) } answers { println("slot = ${slot.captured}") }

        // when
        presenter.discountApply(tickets)

        // then
        assertEquals(slot.captured, MoneyState(8000))
        verify { view.showMoneyText(slot.captured) }
    }

    @Test
    fun `티켓을 등록하고 화면을 전환한다`() {
        // given
        val view = mockk<SeatSelectContract.View>(relaxed = true)
        val presenter = SeatSelectPresenter(view)
        val tickets = sampleTicketsState
        // when
        presenter.addTicket(tickets)

        // then
        verify { view.navigateToConfirmView(tickets) }
    }

    companion object {
        val sampleTicketsState = TicketsState(
            cinemaName = "cinemaName",
            MovieState(
                1,
                "title",
                LocalDate.MIN,
                LocalDate.MIN.plusDays(3),
                listOf(LocalTime.parse("10:00"), LocalTime.parse("12:00")),
                152,
                "description"
            ),
            LocalDateTime.MIN,
            listOf(SeatPositionState(1, 1))
        )
    }
}
