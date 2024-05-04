package woowacourse.movie.presenter.finished

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.ticket.Ticket

@ExtendWith(MockKExtension::class)
class ReservationFinishedPresenterTest {
    @MockK
    private lateinit var view: ReservationFinishedContract.View
    private lateinit var presenter: ReservationFinishedContract.Presenter

    @BeforeEach
    fun setUp() {
        presenter = ReservationFinishedPresenter(view, ScreeningDao(), TheaterDao())
    }

    @Test
    fun `예매한 영화의 제목을 불러온다`() {
        every { view.showMovieTitle(any()) } just runs
        presenter.loadMovie(0)
        verify { view.showMovieTitle(any()) }
    }

    @Test
    fun `예매 내역을 불러온다`() {
        every { view.showReservationHistory(any()) } just runs
        presenter.loadTicket(Ticket(0, 0, Seats(), ScreeningDateTime("", ""), 0))
        verify { view.showReservationHistory(any()) }
    }

    @Test
    fun `선택한 극장 이름을 불러온다`() {
        every { view.showTheaterName(any()) } just runs
        presenter.loadTheater(0)
        verify { view.showTheaterName(any()) }
    }
}
