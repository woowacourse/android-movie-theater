package woowacourse.movie.feature.history

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.ticket.FakeTicketRepository
import woowacourse.movie.feature.movieId
import woowacourse.movie.feature.screeningDate
import woowacourse.movie.feature.screeningTime
import woowacourse.movie.feature.selectedSeats
import woowacourse.movie.feature.theaterName

class ReservationHistoryPresenterTest {
    private lateinit var view: ReservationHistoryContract.View
    private lateinit var presenter: ReservationHistoryContract.Presenter
    private val ticketRepository = FakeTicketRepository()
    private val ticketCount = 3

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = ReservationHistoryPresenter(view)
        repeat(ticketCount) {
            ticketRepository.save(movieId, screeningDate, screeningTime, selectedSeats, theaterName)
        }
    }

    @Test
    fun `저장된 티켓들을 불러온다`() {
        // given
        every { view.displayTickets(any()) } just runs

        // when
        presenter.loadTickets(ticketRepository)

        // then
        verify { view.displayTickets(ticketRepository.findAll()) }
    }
}
