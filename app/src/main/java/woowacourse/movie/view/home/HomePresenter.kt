package woowacourse.movie.view.home

import woowacourse.movie.R
import woowacourse.movie.domain.AdType
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.MovieListItem
import woowacourse.movie.domain.moviesDummy

class HomePresenter(
    private val view: HomeContract.View,
    private val movies: List<Movie> = moviesDummy,
) : HomeContract.Presenter {
    override fun loadMovies() {
        val movieItems = addAdItemToMovieItem(movies)
        view.showMovies(movieItems)
    }

    private fun addAdItemToMovieItem(movies: List<Movie>): List<MovieListItem> {
        return buildList {
            movies.forEachIndexed { index, movie ->
                add(MovieListItem.ItemMovie(movie))
                if ((index + 1) % 3 == 0) {
                    add(
                        MovieListItem.ItemAd(
                            AdType.Banner(
                                imageUrl = R.drawable.advertisement,
                            ),
                        ),
                    )
                }
            }
        }
    }
}
