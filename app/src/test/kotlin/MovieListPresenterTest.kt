import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.data.AdvertisementRepository
import woowacourse.movie.model.Advertisement
import woowacourse.movie.model.Movie
import woowacourse.movie.movielist.MovieListContract
import woowacourse.movie.movielist.MovieListPresenter
import woowacourse.movie.movielist.uimodel.toAdvertisementUiModel
import woowacourse.movie.movielist.uimodel.toMovieUiModel
import woowacourse.movie.usecase.FetchAllMoviesUseCase

@ExtendWith(MockKExtension::class)
class MovieListPresenterTest {
    @RelaxedMockK
    private lateinit var view: MovieListContract.View

    @MockK
    private lateinit var fetchAllMoviesUseCase: FetchAllMoviesUseCase

    @MockK
    private lateinit var advertisementRepository: AdvertisementRepository

    @InjectMockKs
    private lateinit var presenter: MovieListPresenter

    @Test
    fun `리스트에서 영화 3개를 보여주면 광고를 1개 보여준다`() {
        val movieList = List(3) { Movie.STUB_A }
        val advertisementList = List(1) { Advertisement() }
        // given
        every { fetchAllMoviesUseCase() } returns Result.success(movieList)
        every { advertisementRepository.advertisements() } returns advertisementList
        // when
        presenter.loadContents()
        // then
        val expected =
            movieList.map { it.toMovieUiModel() } + advertisementList.map { it.toAdvertisementUiModel() }
        verify { view.showContents(expected) }
    }

    @Test
    fun `영화를 선택하면 극장 목록이 보여진다`() {
        // when
        presenter.selectMovie(1)
        // then
        verify { view.showTheaters(1) }
    }
}
