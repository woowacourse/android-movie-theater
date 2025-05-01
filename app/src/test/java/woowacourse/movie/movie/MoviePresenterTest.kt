package woowacourse.movie.movie

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.HARRY_POTTER
import woowacourse.movie.STAR_IS_BORN
import woowacourse.movie.createMovie
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.Movie

class MoviePresenterTest {
    private lateinit var presenter: MoviePresenter
    private lateinit var mockView: MovieContract.View
    private lateinit var mockMovieList: List<Movie>

    @BeforeEach
    fun setUp() {
        mockView = mockk(relaxed = true)
        mockMovieList =
            listOf(
                createMovie(HARRY_POTTER),
                createMovie(STAR_IS_BORN),
            )

        presenter = MoviePresenter(view = mockView)
    }

    @Test
    fun `지금 예매 버튼을 누르면 다음 화면으로 넘어간다`() {
        val movie =
            createMovie(HARRY_POTTER)

        val movieUiData = movie.toUiModel()
        presenter.setTheaters(movieUiData)

        verify { mockView.showTheaterDialog(any(), movieUiData) }
    }
}
