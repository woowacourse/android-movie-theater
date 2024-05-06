import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.data.DummyMovieRepository
import woowacourse.movie.movielist.MovieListContract
import woowacourse.movie.movielist.MovieListPresenter

@ExtendWith(MockKExtension::class)
class MovieListPresenterTest {
    @RelaxedMockK
    private lateinit var view: MovieListContract.View

    private lateinit var presenter: MovieListContract.Presenter

    @BeforeEach
    fun setUp() {
        presenter = MovieListPresenter(view, DummyMovieRepository)
    }

    @Test
    fun `영화를 선택하면 극장 목록이 보여진다`() {
        presenter.selectMovie(0)
        verify { view.showTheaters(0) }
    }
}
