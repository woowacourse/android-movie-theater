package woowacourse.movie.presentation.movielist

import woowacourse.movie.data.AdData
import woowacourse.movie.domain.model.tools.Movie
import woowacourse.movie.model.data.storage.MovieStorage
import woowacourse.movie.presentation.mappers.toPresentation

class MovieListPresenter(
    override val view: MovieListContract.View,
    private val movieStorage: MovieStorage
) : MovieListContract.Presenter {
    override fun getMovieItemsWithAds() {
        val movies = getMovies().map { MovieItem.Movie(it.toPresentation()) }.toMutableList()
        val ads = AdData.ads.toMutableList()
        val movieItemModels = mutableListOf<MovieItem>()

        repeat(10000) { position ->
            val target: MovieItem = if (position % 4 == 3) {
                ads.removeFirst()
            } else {
                movies.removeFirst()
            }
            movieItemModels.add(target)
        }

        view.updateMovieListView(movieItemModels.toList())
    }

    private fun getMovies(): List<Movie> = movieStorage.getMovies()
}
