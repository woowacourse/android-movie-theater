package woowacourse.movie.ui.main.reservationList

import com.example.domain.repository.TicketsRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class ReservationListPresenterTest {
    @Test
    fun `예매 내역을 가져온다`() {
        // given
        val view = mockk<ReservationListContract.View>(relaxed = true)
        val ticketsRepository = mockk<TicketsRepository>(relaxed = true)
        val presenter = ReservationListPresenter(view, ticketsRepository)

        // when
        every { ticketsRepository.allTickets() } returns listOf()

        presenter.getReservationList()

        // then
        verify { view.setAdapter(listOf()) }
    }
}
