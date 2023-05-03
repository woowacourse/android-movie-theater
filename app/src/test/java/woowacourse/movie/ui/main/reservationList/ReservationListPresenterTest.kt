package woowacourse.movie.ui.main.reservationList

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import woowacourse.movie.data.TicketsRepository

class ReservationListPresenterTest {
    @Test
    fun `예매 내역을 가져온다`() {
        // given
        val ticketsRepository = mockk<TicketsRepository>()
        val presenter = ReservationListPresenter(ticketsRepository)
        every { ticketsRepository.allTickets() } returns emptyList()

        // when
        val reservationList = presenter.getReservationList()

        // then
        assert(reservationList.isEmpty())
        verify { ticketsRepository.allTickets() }
    }
}
