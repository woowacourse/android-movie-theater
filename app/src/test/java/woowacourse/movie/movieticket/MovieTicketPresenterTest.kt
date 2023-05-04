package woowacourse.movie.movieticket

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.contract.movieticket.MovieTicketContract
import woowacourse.movie.presenter.movieticket.MovieTicketPresenter
import woowacourse.movie.ui.model.MovieTicketModel
import woowacourse.movie.ui.model.PeopleCountModel
import woowacourse.movie.ui.model.PriceModel
import woowacourse.movie.ui.model.TicketTimeModel
import java.time.LocalDateTime

class MovieTicketPresenterTest {
    private lateinit var presenter: MovieTicketContract.Presenter
    private lateinit var view: MovieTicketContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter = MovieTicketPresenter(view)
    }

    @Test
    fun `영화 티켓 정보를 보여준다`() {
        // given
        justRun { view.setTextMovieTitle(dummyTicket.title) }
        justRun { view.setTextMovieDate(dummyTicket.time) }
        justRun { view.setTextMovieSeats(dummyTicket.seats) }
        justRun { view.setTextMovieTicketPrice(dummyTicket.price) }

        // when
        presenter.setupTicketInfo(dummyTicket)

        // then
        verify { view.setTextMovieTitle(dummyTicket.title) }
        verify { view.setTextMovieDate(dummyTicket.time) }
        verify { view.setTextMovieSeats(dummyTicket.seats) }
        verify { view.setTextMovieTicketPrice(dummyTicket.price) }
    }

    companion object {
        private val dummyTicket = MovieTicketModel(
            "그레이의 50가지 그림자 1",
            TicketTimeModel(LocalDateTime.of(2023, 5, 1, 13, 0)),
            PeopleCountModel(2),
            emptySet(),
            PriceModel(0)
        )
    }
}
