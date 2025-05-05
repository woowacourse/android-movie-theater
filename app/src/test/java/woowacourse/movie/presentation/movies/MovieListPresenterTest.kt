package woowacourse.movie.presentation.movies

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.MovieData
import woowacourse.movie.fixture.HARRY_POTTER

class MovieListPresenterTest {
    private lateinit var view: MovieListContract.View
    private lateinit var movieData: MovieData
    private lateinit var presenter: MovieListContract.Presenter

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        movieData = mockk(relaxed = true)
        presenter = MovieListPresenter(view, movieData)
    }

    @Test
    fun `영화 목록을 가져와서 화면에 출력한다`() {
        // when
        presenter.loadMovieList()

        // then
        verify { view.showMovieList(any()) }
    }

    @Test
    fun `버튼을 누르면 예매 화면으로 이동한다`() {
        // when
        presenter.selectTheater(HARRY_POTTER)

        // then
        verify { view.showTheaterList(HARRY_POTTER) }
    }
}
