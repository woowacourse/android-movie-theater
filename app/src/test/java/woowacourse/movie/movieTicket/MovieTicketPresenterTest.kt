package woowacourse.movie.movieTicket

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.PeopleCountModel
import woowacourse.movie.model.PriceModel
import woowacourse.movie.model.TicketTimeModel
import woowacourse.movie.view.movieTicket.MovieTicketContract
import woowacourse.movie.view.movieTicket.MovieTicketPresenter
import java.time.LocalDateTime

class MovieTicketPresenterTest {
    private lateinit var presenter: MovieTicketContract.Presenter
    private lateinit var view: MovieTicketContract.View

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = MovieTicketPresenter(view)
    }

    @Test
    fun `영화 티켓 정보를 보여준다`() {
        // given
        justRun { view.setTextMovieTitle(dummyTicket.title) }
        justRun { view.setTextMovieDate(dummyTicket.time) }
        justRun { view.setTextMovieSeats(dummyTicket.seats, dummyTicket.theater) }
        justRun { view.setTextMovieTicketPrice(dummyTicket.price) }

        // when
        presenter.setupTicketInfo(dummyTicket)

        // then
        verify { view.setTextMovieTitle(dummyTicket.title) }
        verify { view.setTextMovieDate(dummyTicket.time) }
        verify { view.setTextMovieSeats(dummyTicket.seats, dummyTicket.theater) }
        verify { view.setTextMovieTicketPrice(dummyTicket.price) }
    }

    companion object {
        private val dummyTicket = MovieTicketModel(
            "횡성 극장",
            "그레이의 50가지 그림자 1",
            TicketTimeModel(LocalDateTime.of(2023, 5, 1, 13, 0)),
            PeopleCountModel(2),
            emptySet(),
            PriceModel(0)
        )
    }
}
