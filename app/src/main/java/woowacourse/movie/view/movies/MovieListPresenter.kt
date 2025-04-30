package woowacourse.movie.view.movies

import woowacourse.movie.data.MovieStore
import woowacourse.movie.data.TheaterStore
import woowacourse.movie.domain.model.ad.Advertisement
import woowacourse.movie.view.movies.model.UiModel
import woowacourse.movie.view.movies.model.toUiModel

class MovieListPresenter(
    private val view: MovieListContract.View,
    private val movies: MovieStore,
    private val theaters: TheaterStore,
) : MovieListContract.Presenter {
    override fun loadUiData() {
        val items = mutableListOf<UiModel>()
        movies.getAll().forEachIndexed { index, movie ->
            items.add(movie.toUiModel())
            if ((index + 1) % AD_DIVIDE_STANDARD == 0) {
                items.add(Advertisement().toUiModel())
            }
        }

        view.showMovieList(items)
    }

    override fun loadTheaters(movieId: Int) {
        val theaters = theaters.createTheaters()
        view.showTheaterBottomSheet(movieId, theaters)
    }

    companion object {
        private const val AD_DIVIDE_STANDARD = 3
    }
}
