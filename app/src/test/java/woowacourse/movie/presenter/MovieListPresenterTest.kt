package woowacourse.movie.presenter

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.feed.Feed
import woowacourse.movie.domain.model.feed.FeedGenerator
import woowacourse.movie.view.home.model.FeedUiModel
import woowacourse.movie.view.home.movies.MovieListContract
import woowacourse.movie.view.home.movies.MovieListPresenter

class MovieListPresenterTest {
    private val view: MovieListContract.View = mockk<MovieListContract.View>(relaxed = true)
    private lateinit var feed: List<Feed>

    @BeforeEach
    fun setUp() {
        feed = FeedGenerator().generate()
    }

    @Test
    fun `영화 리스트를 로딩하면 영화와 광고가 포함된 리스트를 View에 전달한다`() {
        val presenter = MovieListPresenter(view)

        presenter.loadMovies()

        verify {
            view.showMovieList(
                match { uiModels ->
                    uiModels[0] is FeedUiModel.MovieUiModel &&
                        uiModels[1] is FeedUiModel.MovieUiModel &&
                        uiModels[2] is FeedUiModel.MovieUiModel &&
                        uiModels[3] is FeedUiModel.AdvertisementUiModel
                },
            )
        }
    }
}
