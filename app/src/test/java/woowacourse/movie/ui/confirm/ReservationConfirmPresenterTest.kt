package woowacourse.movie.ui.confirm

import android.net.Uri
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

class ReservationConfirmPresenterTest {
    @Test
    fun `예약 확인 화면을 초기화한다`() {
        // given
        val view = mockk<ReservationConfirmContract.View>()
        val presenter = ReservationConfirmPresenter(view)
        val tickets = sampleTicketsState
        every { view.setTicket(any()) } returns Unit
        every { view.registerNotification(any()) } returns Unit

        // when
        presenter.init(tickets)

        // then
        verify { view.setTicket(any()) }
        verify { view.registerNotification(any()) }
    }

    @Test
    fun `가격 할인 정책을 적용하면 화면 금액이 바뀐다`() {
        // given
        val view = mockk<ReservationConfirmContract.View>(relaxed = true)
        val presenter = ReservationConfirmPresenter(view)
        val slot = slot<MoneyState>()
        val tickets = sampleTicketsState
        every {
            view.setMoneyTextView(capture(slot))
        } answers { println("slot = ${slot.captured}") }

        // when
        presenter.discountApplyMoney(tickets)

        // then
        assertEquals(slot.captured, MoneyState(8000))
        verify { view.setMoneyTextView(slot.captured) }
    }

    companion object {
        val sampleTicketsState = TicketsState(
            cinemaName = "cinemaName",
            MovieState(
                Uri.parse(""),
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
