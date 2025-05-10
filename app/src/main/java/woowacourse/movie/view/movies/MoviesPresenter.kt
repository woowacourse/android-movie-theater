package woowacourse.movie.view.movies

import woowacourse.movie.db.DummyAdvertisement
import woowacourse.movie.db.DummyMovie
import woowacourse.movie.domain.model.Movie

class MoviesPresenter(
    private val view: MoviesContract.View,
) : MoviesContract.Presenter {
    override fun loadData() {
        view.showMovies(buildListWithAds(DummyMovie.baseDummyMovies))
    }

    private fun buildListWithAds(movies: List<Movie>): List<MovieListItem> {
        val result = mutableListOf<MovieListItem>()
        movies.forEachIndexed { index, movie ->
            result.add(MovieListItem.MovieItem(movie))
            if ((index + INDEX_INTERVAL) % AD_INTERVAL == ZERO) {
                result.add(MovieListItem.AdItem(DummyAdvertisement.advertisement))
            }
        }
        return result
    }

    companion object {
        private const val INDEX_INTERVAL = 1
        private const val AD_INTERVAL = 3
        private const val ZERO = 0
    }
}
