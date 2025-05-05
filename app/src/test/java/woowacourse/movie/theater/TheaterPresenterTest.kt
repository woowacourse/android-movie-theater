package woowacourse.movie.theater

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.fixture.HARRY_POTTER
import woowacourse.movie.fixture.createMovie
import woowacourse.movie.fixture.createTheater
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.Movie
import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel

class TheaterPresenterTest {
    private lateinit var presenter: TheaterPresenter
    private lateinit var mockView: TheaterContract.View
    private lateinit var movie: Movie
    private lateinit var movieUiModel: MovieUiModel
    private lateinit var theaters: ArrayList<TheaterUiModel>

    @BeforeEach
    fun setUp() {
        mockView = mockk(relaxed = true)
        movie = createMovie(HARRY_POTTER)
        theaters =
            arrayListOf(
                createTheater("메가박스 강남", movie),
                createTheater("롯데시네마 건대입구", movie),
            )

        movieUiModel = movie.toUiModel()

        presenter = TheaterPresenter(mockView)
    }

    @Test
    fun `초기화시 극장 목록을 View에 보여준다`() {
        every { mockView.showTheaters(theaters) } just Runs

        presenter.initialize(movieUiModel, theaters)

        verify { mockView.showTheaters(theaters) }
    }

    @Test
    fun `극장 클릭시 영화와 함께 예약 상세 화면으로 이동한다`() {
        val selectedTheater = theaters[0]
        every { mockView.navigateToBookingDetail(selectedTheater, movieUiModel) } just Runs

        presenter.initialize(movieUiModel, theaters)
        presenter.clickTheater(selectedTheater)

        verify { mockView.navigateToBookingDetail(selectedTheater, movieUiModel) }
    }
}
