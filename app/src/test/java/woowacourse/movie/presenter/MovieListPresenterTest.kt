package woowacourse.movie.presenter

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.MovieStore
import woowacourse.movie.data.TheaterStore
import woowacourse.movie.domain.model.movies.Movie
import woowacourse.movie.view.home.movies.MovieListContract
import woowacourse.movie.view.home.movies.MovieListPresenter
import woowacourse.movie.view.home.movies.model.Item

class MovieListPresenterTest {
    private val view: MovieListContract.View = mockk<MovieListContract.View>(relaxed = true)
    private lateinit var model: List<Movie>

    @BeforeEach
    fun setUp() {
        model = MovieStore().getAll()
    }

    @Test
    fun `영화 리스트를 로딩하면 영화와 광고가 포함된 리스트를 View에 전달한다`() {
        MovieListPresenter(view, MovieStore(), TheaterStore())

        verify {
            view.showMovieList(
                match { uiModels ->
                    uiModels[0] is Item.MovieItem &&
                        uiModels[1] is Item.MovieItem &&
                        uiModels[2] is Item.MovieItem &&
                        uiModels[3] is Item.AdvertiseItem
                },
            )
        }
    }
}
