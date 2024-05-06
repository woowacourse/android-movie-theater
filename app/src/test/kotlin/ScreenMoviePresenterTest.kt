import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import woowacourse.movie.data.DummyMovies
import woowacourse.movie.model.Movie
import woowacourse.movie.model.Movies
import woowacourse.movie.screeningmovie.ScreenMoviePresenter
import woowacourse.movie.screeningmovie.ScreeningMovieContract
import woowacourse.movie.screeningmovie.toScreenItems

class ScreenMoviePresenterTest {
    private lateinit var view: ScreeningMovieContract.View

    private lateinit var presenter: ScreenMoviePresenter

    @BeforeEach
    fun setUp() {
        view = mockk<ScreeningMovieContract.View>()
        presenter = ScreenMoviePresenter(view, DummyMovies)
    }

    @Test
    @DisplayName("영화 목록을 가져오면, 광고를 넣어서 뷰에 전달한다")
    fun add_advertise_When_load_movie_list() {
        // when
        val expectedMovies = listOf(Movie.STUB)
        val moviesWithAds =
            Movies(expectedMovies).insertAdvertisements(
                ADVERTISEMENT_INTERVAL,
            )
        every { view.showMovies(moviesWithAds.toScreenItems()) } just Runs

        // given
        presenter.loadScreeningMovies()

        // then
        verify { view.showMovies(moviesWithAds.toScreenItems()) }
    }

    companion object {
        private const val ADVERTISEMENT_INTERVAL = 3
    }
}
