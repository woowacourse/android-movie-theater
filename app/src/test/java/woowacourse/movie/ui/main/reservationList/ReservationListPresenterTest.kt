package woowacourse.movie.ui.main.reservationList

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.dbHelper.TicketsDbHelper

class ReservationListPresenterTest {
    private lateinit var view: ReservationListContract.View
    private lateinit var ticketsDbHelper: TicketsDbHelper

    private lateinit var presenter: ReservationListPresenter

    @Before
    fun setUp() {
        // given
        view = mockk(relaxed = true)
        ticketsDbHelper = mockk(relaxed = true)
        presenter = ReservationListPresenter(view, ticketsDbHelper)
    }

    @Test
    fun `예매 내역을 가져온다`() {
        // given
        every { ticketsDbHelper.getAll() } returns listOf()

        // when
        presenter.setUpReservationList()

        // then
        verify { view.showTickets(listOf()) }
    }
}
