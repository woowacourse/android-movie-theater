package woowacourse.movie.ui.main.reservationList

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.dbHelper.TicketsDbHelper
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.TicketsState

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
        val fakeTickets = getFakeTickets()
        every { ticketsDbHelper.getAll() } returns fakeTickets

        // when
        presenter.setUpReservationList()

        // then
        verify { view.showTickets(fakeTickets) }
    }

    fun getFakeTickets() = listOf(
        TicketsState(
            movieName = "영화1",
            cinemaName = "영화관1",
            dateTime = mockk(),
            positions = getFakeSeatPositions()
        )
    )

    fun getFakeSeatPositions() = listOf(
        SeatPositionState(1, 1),
        SeatPositionState(1, 2),
        SeatPositionState(1, 3)
    )
}
