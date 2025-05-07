package woowacourse.movie.view.movies

import woowacourse.movie.data.DummyAdvertisement
import woowacourse.movie.data.DummyMovie
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieListItem
import woowacourse.movie.domain.model.MovieListItemRule
import woowacourse.movie.domain.model.MovieListItemRuleImpl

class MoviesPresenter(
    private val view: MoviesContract.View,
) : MoviesContract.Presenter {
    private val movieListItemRule: MovieListItemRule<MovieListItem> = MovieListItemRuleImpl()

    override fun loadData() {
        view.showMovies(buildListWithAds(DummyMovie.dummyMovie))
    }

    private fun buildListWithAds(movies: List<Movie>): List<MovieListItem> =
        buildList {
            var movieSize = 0
            var index = 0
            while (movieSize < movies.size) {
                val clazz = movieListItemRule.whenIndex(index)
                when (clazz) {
                    MovieListItem.MovieItem::class.java -> {
                        add(MovieListItem.MovieItem(movies[movieSize]))
                        movieSize++
                    }
                    MovieListItem.AdItem::class.java -> {
                        add(MovieListItem.AdItem(DummyAdvertisement.advertisement))
                    }
                }
                index++
            }
        }
}
