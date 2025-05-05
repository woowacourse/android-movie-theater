package woowacourse.movie.presenter.movies

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.DummyAdvertisement
import woowacourse.movie.data.DummyMovie
import woowacourse.movie.view.movies.MovieListItem
import woowacourse.movie.view.movies.MoviesContract
import woowacourse.movie.view.movies.MoviesPresenter

class MoviesPresenterTest {
    private lateinit var presenter: MoviesPresenter
    private lateinit var view: MoviesContract.View

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = MoviesPresenter(view)
    }

    @Test
    fun `영화 리스트를 보여준다`() {
        // given
        every { view.showMovies(any()) } just Runs
        val result =
            buildList {
                DummyMovie.dummyMovie.forEachIndexed { index, movie ->
                    add(MovieListItem.MovieItem(movie))
                    if ((index + 1) % 3 == 0) {
                        add(MovieListItem.AdItem(DummyAdvertisement.advertisement))
                    }
                }
            }

        // when
        presenter.loadData()
        // then
        verify { view.showMovies(result) }
    }
}
