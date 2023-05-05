package woowacourse.movie

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.main.MovieMapper.toUiModel
import woowacourse.movie.model.main.MovieUiModel
import woowacourse.movie.movie.MovieRepository
import woowacourse.movie.theater.TheaterRepository
import woowacourse.movie.ui.completed.CompletedContract
import woowacourse.movie.ui.completed.CompletedPresenter

class CompletedPresenterTest {

    private lateinit var completedPresenter: CompletedContract.Presenter
    private lateinit var view: CompletedContract.View

    @Before
    fun setUp() {
        view = mockk()

        // given id가 1인 movie와 theater에 대한 예약정보를 받는다
        completedPresenter = CompletedPresenter(
            view = view,
            reservation = ReservationUiModel(
                movieId = 1,
                theaterId = 1
            )
        )
    }

    @Test
    fun `movieId와 theaterId를 이용해 저장소로부터 영화 정보와 영화관의 정보를 받아 view를 갱신한다`() {
        // given
        val slotMovie = slot<MovieUiModel>()
        val slotTheater = slot<String>()

        every {
            view.initView(
                movie = capture(slotMovie),
                theaterName = capture(slotTheater)
            )
        } just Runs

        // when
        completedPresenter.initReservation()

        // then
        val actualMovie = slotMovie.captured
        val actualTheater = slotTheater.captured
        val expectedMovie = MovieRepository.getMovie(1).toUiModel()
        val expectedTheater = TheaterRepository.getTheater(1).name

        assertEquals(expectedMovie, actualMovie)
        assertEquals(expectedTheater, actualTheater)
        verify {
            view.initView(
                movie = actualMovie,
                theaterName = actualTheater
            )
        }
    }
}