package woowacourse.movie.feature.finished

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.TestFixture.mockMovies
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.ticket.Ticket
import java.time.LocalDate
import java.time.LocalTime

@ExtendWith(MockKExtension::class)
class ReservationFinishedPresenterTest {
    @MockK
    private lateinit var view: ReservationFinishedContract.View
    private lateinit var presenter: ReservationFinishedContract.Presenter
    private val movie = mockMovies[0]
    private val mockTicket =
        Ticket(
            movieId = 0,
            theaterName = "선릉 극장",
            Seats(),
            ScreeningDateTime(LocalDate.of(2024, 5, 6), LocalTime.of(10, 0)),
            32_000,
        )

    @BeforeEach
    fun setUp() {
        presenter = ReservationFinishedPresenter(view, ScreeningDao(), mockTicket)
    }

    @Test
    fun `예매한 영화의 제목을 보여준다`() {
        every { view.showMovieTitle(any()) } just runs
        presenter.loadMovie()
        verify { view.showMovieTitle(movie) }
    }

    @Test
    fun `예매 내역을 보여준다`() {
        every { view.showReservationHistory(any()) } just runs
        presenter.loadTicket()
        verify { view.showReservationHistory(mockTicket) }
    }
}
