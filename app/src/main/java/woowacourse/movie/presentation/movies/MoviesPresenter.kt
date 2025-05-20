package woowacourse.movie.presentation.movies

import woowacourse.movie.data.MovieData
import woowacourse.movie.domain.model.Advertise
import woowacourse.movie.domain.model.movie.Movie

class MoviesPresenter(
    private val view: MoviesContract.View,
    private val movieData: MovieData,
) : MoviesContract.Presenter {
    override fun initializeMovies() {
        val screeningMovies = movieData.getData()
        val moviesItems = Advertise(screeningMovies).insertAdvertisement()
        view.showMovies(moviesItems)
    }

    override fun selectMovie(movie: Movie) {
        view.showTheaterSelectDialog(movie)
    }
}
