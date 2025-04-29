package woowacourse.movie.presentation.movies

import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.data.MovieData

class MoviesPresenter(
    private val view: MoviesContract.View,
    private val movieData: MovieData,
) : MoviesContract.Presenter {
    override fun onViewCreated() {
        val screeningMovies = movieData.getData()
        view.showMovies(screeningMovies)
    }

    override fun onMovieClicked(movie: Movie) {
        view.navigateToBooking(movie)
    }
}
