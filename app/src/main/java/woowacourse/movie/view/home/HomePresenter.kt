package woowacourse.movie.view.home

import woowacourse.movie.domain.AdvertisementId
import woowacourse.movie.domain.Movie
import woowacourse.movie.view.home.movies.MovieItem
import woowacourse.movie.view.home.movies.MovieUi
import woowacourse.movie.view.home.movies.toMovieUi

class HomePresenter(
    private val view: HomeContract.View,
) : HomeContract.Presenter {
    override fun fetchData() {
        val movies: List<Movie> = Movie.dummy
        val movieItem = convertToMovieItems(movies.map { it.toMovieUi() })
        view.showMoviesScreen(movieItem)
    }

    private fun convertToMovieItems(movieUis: List<MovieUi>): List<MovieItem> {
        val items = mutableListOf<MovieItem>()
        movieUis.forEachIndexed { index, movieUi ->
            items.add(MovieItem.ScreeningMovieUi(movieUi))
            if (index.isAdInsertionPosition()) {
                items.add(MovieItem.Advertisement(AdvertisementId.Woowa))
            }
        }
        return items
    }

    private fun Int.isAdInsertionPosition(): Boolean {
        return (this + INDEX_OFFSET) % AD_INSERT_INTERVAL == 0
    }

    companion object {
        private const val AD_INSERT_INTERVAL = 3
        private const val INDEX_OFFSET = 1
    }
}
