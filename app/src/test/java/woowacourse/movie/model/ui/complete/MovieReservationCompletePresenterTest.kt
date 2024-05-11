package woowacourse.movie.model.ui.complete

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.database.ticket.TicketEntity
import woowacourse.movie.model.ui.repository.FakeTicketRepository
import woowacourse.movie.ui.complete.MovieReservationCompleteContract
import woowacourse.movie.ui.complete.MovieReservationCompletePresenter

class MovieReservationCompletePresenterTest {
    private lateinit var presenter: MovieReservationCompletePresenter
    private lateinit var view: MovieReservationCompleteContract.View

    @BeforeEach
    fun setUp() {
        view = mockk<MovieReservationCompleteContract.View>(relaxed = true)
        presenter = MovieReservationCompletePresenter(view, FakeTicketRepository)
        FakeTicketRepository.save(
            TicketEntity(
                title = "",
                theater = "강남",
                screeningStartDateTime = "2024-03-03 11:00",
                reservationCount = 1,
                seats = "A1",
                price = 10000
            ),
        )
    }

    @Test
    fun `티켓 정보가 없을 경우 에러를 보여준다`() {
        // given

        // when
        presenter.loadTicket(-1L)

        // then
        verify { view.showError(any()) }
    }

    @Test
    fun `에러를 처리한다`() {
        // given

        // when
        presenter.handleError(Throwable())

        // then
        verify { view.showError(any()) }
    }
}
