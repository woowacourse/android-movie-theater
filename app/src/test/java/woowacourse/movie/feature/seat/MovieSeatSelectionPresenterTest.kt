package woowacourse.movie.feature.seat

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.ticket.FakeTicketRepository
import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.feature.firstMovie
import woowacourse.movie.feature.firstMovieId
import woowacourse.movie.feature.invalidMovieId
import woowacourse.movie.feature.movieId
import woowacourse.movie.feature.screeningDate
import woowacourse.movie.feature.screeningTime
import woowacourse.movie.feature.selectedSeats
import woowacourse.movie.feature.theaterName

class MovieSeatSelectionPresenterTest {
    private lateinit var view: MovieSeatSelectionContract.View
    private lateinit var presenter: MovieSeatSelectionPresenter
    private lateinit var ticketRepository: TicketRepository
    private val movie = firstMovie

    @BeforeEach
    fun setUp() {
        view = mockk()
        ticketRepository = FakeTicketRepository()
        presenter = MovieSeatSelectionPresenter(view)
        presenter.updateSelectedSeats(selectedSeats)
    }

    @Test
    fun `영화 id의 영화 제목을 불러온다`() {
        // given
        every { view.displayMovieTitle(any()) } just runs

        // when
        presenter.loadMovieTitle(firstMovieId)

        // then
        verify { view.displayMovieTitle(movie.title) }
    }

    @Test
    fun `존재하지 않는 영화 id의 경우 영화 제목을 불러오면 에러 메시지를 보여준다`() {
        // given
        every { view.showToastInvalidMovieIdError(any()) } just runs

        // when
        presenter.loadMovieTitle(invalidMovieId)

        // then
        verify { view.showToastInvalidMovieIdError(any()) }
    }

    @Test
    fun `영화 좌석 정보를 초기화한다`() {
        // given
        every { view.setUpTableSeats(any()) } just runs

        // when
        presenter.loadTableSeats(selectedSeats)

        // then
        verify { view.setUpTableSeats(any()) }
    }

    @Test
    fun `좌석을 선택한다`() {
        // given
        every { view.updateSeatBackgroundColor(any(), any()) } just runs
        every { view.updateSelectResult(any()) } just runs

        // when
        presenter.selectSeat(0)

        // then
        verify { view.updateSeatBackgroundColor(0, false) }
        verify { view.updateSelectResult(any()) }
    }

    @Test
    fun `영화를 예매하면 예매 알림을 등록한다`() {
        // given
        every { view.navigateToResultView(any()) } just runs
        every { view.setTicketAlarm(any()) } just runs

        // when
        presenter.reserveMovie(
            ticketRepository,
            movieId,
            screeningDate,
            screeningTime,
            selectedSeats,
            theaterName,
        )

        // then
        verify { view.navigateToResultView(0L) }
        verify { view.setTicketAlarm(ticketRepository.find(0L)) }
    }
}
