package woowacourse.movie.view.home.movies

import woowacourse.movie.data.MovieStore
import woowacourse.movie.domain.model.ad.Advertisement
import woowacourse.movie.view.home.model.UiModel
import woowacourse.movie.view.home.model.toUiModel

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val movieStore: MovieStore,
) : MovieListContract.Presenter {
    override fun loadMovies() {
        val items = mutableListOf<UiModel>()
        movieStore.getAll().forEachIndexed { index, movie ->
            items.add(movie.toUiModel())
            if ((index + 1) % AD_DIVIDE_STANDARD == 0) {
                items.add(Advertisement().toUiModel())
            }
        }
        view.showMovieList(items)
    }

    override fun selectMovie(movieId: Int) {
        view.moveToTheaterSelection(movieId)
    }

    companion object {
        private const val AD_DIVIDE_STANDARD = 3
    }
}
