import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.model.Advertisement
import woowacourse.movie.model.Movie
import woowacourse.movie.movielist.MovieListContract
import woowacourse.movie.movielist.MovieListPresenter
import woowacourse.movie.movielist.uimodel.toAdvertisementUiModel
import woowacourse.movie.movielist.uimodel.toMovieUiModel
import woowacourse.movie.repository.MovieRepository

@ExtendWith(MockKExtension::class)
class MovieListPresenterTest {
    @MockK
    private lateinit var view: MovieListContract.View

    @MockK
    private lateinit var repository: MovieRepository

    @InjectMockKs
    private lateinit var presenter: MovieListPresenter

    @Test
    fun `리스트에서 영화 3개를 보여주면 광고를 1개 보여준다`() {
        val movieList = List(3) { Movie.STUB_A }
        val advertisementList = List(1) { Advertisement() }
        // given
        every { repository.movies() } returns movieList
        every { repository.advertisements() } returns advertisementList
        every { view.showContents(any()) } just runs
        // when
        presenter.loadContents()
        // then
        val expected =
            movieList.map { it.toMovieUiModel() } + advertisementList.map { it.toAdvertisementUiModel() }
        verify { view.showContents(expected) }
    }

    @Test
    fun `영화를 선택하면 극장 목록이 보여진다`() {
        // given
        every { view.showTheaters(0) } just runs
        // when
        presenter.selectMovie(0)
        // then
        verify { view.showTheaters(0) }
    }
}
