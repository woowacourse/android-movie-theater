package woowacourse.movie.view.home.movies

import woowacourse.movie.domain.model.feed.FeedGenerator
import woowacourse.movie.view.home.model.toUiModel

class MovieListPresenter(
    private val view: MovieListContract.View,
) : MovieListContract.Presenter {
    override fun loadMovies() {
        val feed = FeedGenerator().generate().map { item -> item.toUiModel() }
        view.showMovieList(feed)
    }

    override fun selectMovie(movieId: Int) {
        view.moveToTheaterSelection(movieId)
    }
}
