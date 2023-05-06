package woowacourse.movie.ui.main.reservationList

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import woowacourse.movie.dbHelper.TicketsDbHelper

class ReservationListPresenterTest {
    @Test
    fun `예매 내역을 가져온다`() {
        // given
        val view = mockk<ReservationListContract.View>(relaxed = true)
        val ticketsDbHelper = mockk<TicketsDbHelper>(relaxed = true)
        val presenter = ReservationListPresenter(view, ticketsDbHelper)

        every { ticketsDbHelper.getAll() } returns listOf()

        // when
        presenter.getReservationList()

        // then
        verify { view.setAdapter(listOf()) }
    }
}
