package woowacourse.movie.model.ui.complete

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.model.A1_SEAT
import woowacourse.movie.model.db.UserTicket
import woowacourse.movie.model.db.UserTicketRepositoryImpl
import woowacourse.movie.model.ui.FakeUserTicketDao
import woowacourse.movie.ui.complete.MovieReservationCompleteContract
import woowacourse.movie.ui.complete.MovieReservationCompletePresenter
import java.time.LocalDateTime

class MovieReservationCompletePresenterTest {
    private lateinit var presenter: MovieReservationCompletePresenter
    private lateinit var view: MovieReservationCompleteContract.View

    @BeforeEach
    fun setUp() {
        UserTicketRepositoryImpl.initializeRepository(FakeUserTicketDao())
        UserTicketRepositoryImpl.get().apply {
            insert(testUserTicket)
        }
        view = mockk<MovieReservationCompleteContract.View>(relaxed = true)
        presenter = MovieReservationCompletePresenter(view, UserTicketRepositoryImpl.get())
    }

    @Test
    fun `티켓 정보를 보여준다`() {
        // given

        // when
        presenter.loadTicket(0L)

        // then
        verify { view.showTicket(testUserTicket) }
    }

    @Test
    fun `티켓 정보가 없을 경우 에러를 보여준다`() {
        presenter.loadTicket(-1L)

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

    companion object {
        private val testUserTicket =
            UserTicket(
                movieTitle = "",
                screeningStartDateTime = LocalDateTime.of(2024, 3, 28, 21, 0),
                reservationCount = 1,
                reservationSeats = listOf(A1_SEAT),
                theaterName = "선릉",
                reservationAmount = 10000,
            )
    }
}
