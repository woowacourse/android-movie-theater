package woowacourse.movie.feature.result

import android.content.Context
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.movie.MovieRepositoryImpl
import woowacourse.movie.data.ticket.FakeTicketRepository
import woowacourse.movie.feature.movieId
import woowacourse.movie.feature.screeningDate
import woowacourse.movie.feature.screeningTime
import woowacourse.movie.feature.selectedSeats
import woowacourse.movie.feature.theaterName

class MovieResultPresenterTest {
    private lateinit var view: MovieResultContract.View
    private lateinit var applicationContext: Context
    private lateinit var presenter: MovieResultPresenter
    private val ticketRepository = FakeTicketRepository()

    @BeforeEach
    fun setUp() {
        view = mockk()
        applicationContext = mockk()
        presenter = MovieResultPresenter(view, applicationContext, ticketRepository)
    }

    @Test
    fun `예매한 영화 티켓의 정보가 보여진다`() {
        // given
        every { view.displayTicket(any(), any()) } just runs
        val ticketId =
            ticketRepository.save(movieId, screeningDate, screeningTime, selectedSeats, theaterName)

        // when
        presenter.loadTicket(ticketId)

        // then
        val ticket = ticketRepository.find(ticketId)
        val movie = MovieRepositoryImpl.find(ticket.movieId)
        verify { view.displayTicket(ticket, movie) }
    }

    @Test
    fun `티켓의 정보가 없다면 예외가 발생한다`() {
        // given
        every { view.showToastInvalidMovieIdError(any()) } just runs

        // when
        presenter.loadTicket(-1)

        // then
        verify { view.showToastInvalidMovieIdError(any()) }
    }
}
