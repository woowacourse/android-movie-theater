package woowacourse.movie.view.home

import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Showings
import woowacourse.movie.domain.moviesDummy

class HomePresenter(
    private val view: HomeContract.View,
) : HomeContract.Presenter {
    override fun loadMovies() {
        val movies: List<Movie> = moviesDummy
        view.showMovies(movies)
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
