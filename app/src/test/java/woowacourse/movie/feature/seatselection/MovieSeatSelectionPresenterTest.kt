package woowacourse.movie.feature.seatselection

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.FakeTicketRepository
import woowacourse.movie.data.TicketRepository
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
    fun `loadMovieTitle를 호출하면 선택된 영화의 제목이 보여진다`() {
        // Given
        every { view.displayMovieTitle(any()) } just runs

        // When
        presenter.loadMovieTitle(firstMovieId)

        // Then
        verify { view.displayMovieTitle(movie.title) }
    }

    @Test
    fun `영화 데이터가 없는 경우 loadMovieTitle을 호출하면 에러 메시지를 보여준다`() {
        // Given
        every { view.showToastInvalidMovieIdError(any()) } just runs

        // When
        presenter.loadMovieTitle(invalidMovieId)

        // Then
        verify { view.showToastInvalidMovieIdError(any()) }
    }

    @Test
    fun `loadTableSeats를 호출하면 테이블 좌석들의 정보가 세팅된다`() {
        // Given
        every { view.setUpTableSeats(any()) } just runs

        // When
        presenter.loadTableSeats(selectedSeats)

        // Then
        verify { view.setUpTableSeats(any()) }
    }

    @Test
    fun `clickTableSeat를 호출하면 좌석의 배경색이 바뀌고 선택 결과가 화면에 보여진다`() {
        // Given
        every { view.updateSeatBackgroundColor(any(), any()) } just runs
        every { view.updateSelectResult(any()) } just runs

        // When
        presenter.clickTableSeat(0)

        // Then
        verify { view.updateSeatBackgroundColor(0, false) }
        verify { view.updateSelectResult(any()) }
    }

    @Test
    fun `clickPositiveButton를 호출하면 예매 알림을 등록하고 예매 결과를 보여주는 화면으로 이동한다`() {
        // given
        every { view.navigateToResultView(any()) } just runs
        every { view.setTicketAlarm(any()) } just runs

        // when
        presenter.clickPositiveButton(
            ticketRepository,
            movieId,
            screeningDate.toString(),
            screeningTime.toString(),
            selectedSeats,
            theaterName,
        )
        Thread.sleep(1000)

        // then
        verify { view.navigateToResultView(0L) }
        verify { view.setTicketAlarm(ticketRepository.find(0L)) }
    }
}
