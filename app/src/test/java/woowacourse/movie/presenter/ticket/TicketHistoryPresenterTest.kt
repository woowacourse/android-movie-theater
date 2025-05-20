package woowacourse.movie.presenter.ticket

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.datasource.TicketDataSource
import woowacourse.movie.domain.reservation.PurchaseType
import woowacourse.movie.domain.reservation.Seat
import woowacourse.movie.domain.ticket.FakeCancelTimePolicy
import woowacourse.movie.domain.ticket.TicketHistory
import woowacourse.movie.ui.view.ticket.TicketContract
import woowacourse.movie.ui.view.ticket.TicketPresenter
import java.time.LocalDateTime

class TicketHistoryPresenterTest {
    private lateinit var view: TicketContract.View
    private lateinit var ticketDataSource: TicketDataSource

    private val ticketId = 2L
    private val ticket =
        TicketHistory(
            id = ticketId,
            title = "해리 포터와 마법사의 돌",
            showtime = LocalDateTime.of(2025, 4, 15, 11, 0),
            count = 2,
            seats = setOf(Seat(1, 1), Seat(2, 2)),
            cinemaName = "선릉 극장",
            purchaseType = PurchaseType.DEFAULT,
        )

    private lateinit var presenter: TicketPresenter

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        ticketDataSource = mockk()
        stubTicketDataSource()
        presenter = TicketPresenter(view, ticketDataSource, ticketId, FakeCancelTimePolicy(15))
    }

    private fun stubTicketDataSource() {
        every { ticketDataSource.getTicket(ticketId, any()) } answers {
            val callback = arg<(TicketHistory) -> Unit>(1)
            callback(ticket)
        }
    }

    @Test
    fun `상영 취소 관련 안내를 표시한다`() {
        verify { view.setCancelDescription(15) }
    }

    @Test
    fun `영화 제목을 표시한다`() {
        verify { view.setMovieTitle("해리 포터와 마법사의 돌") }
    }

    @Test
    fun `영화 상영 시간을 표시한다`() {
        verify { view.setShowtime(LocalDateTime.of(2025, 4, 15, 11, 0)) }
    }

    @Test
    fun `예약 인원을 표시한다`() {
        verify {
            view.setCount(
                2,
                setOf(Seat(1, 1), Seat(2, 2)),
                "선릉 극장",
            )
        }
    }

    @Test
    fun `금액을 표시한다`() {
        verify { view.setPrice(26_000) }
    }
}
