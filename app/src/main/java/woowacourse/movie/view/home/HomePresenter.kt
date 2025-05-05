package woowacourse.movie.view.home

import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.MovieItem
import woowacourse.movie.domain.Showings
import woowacourse.movie.domain.moviesDummy

class HomePresenter(
    private val view: HomeContract.View,
) : HomeContract.Presenter {
    override fun loadMovies() {
        val movies: List<Movie> = moviesDummy
        val movieItems = addAdItemToMovieItem(movies)

        view.showMovies(movieItems)
    }

    private fun addAdItemToMovieItem(movies: List<Movie>): MutableList<MovieItem> {
        val movieItems = mutableListOf<MovieItem>()

        movies.forEachIndexed { index, movie ->
            movieItems.add(MovieItem.ItemMovie(movie))
            if ((index + 1) % 3 == 0) {
                movieItems.add(MovieItem.ItemAd)
            }
        }
        return movieItems
    }

    override fun onMovieSelected(movie: Movie) {
        view.showTheaterSelectDialog(
            movie = movie,
        )
    }

    override fun onTheaterSelected(
        movie: Movie,
        showings: Showings,
    ) {
        view.navigateToReservation(
            movie = movie,
            showings = showings,
        )
    }
}
