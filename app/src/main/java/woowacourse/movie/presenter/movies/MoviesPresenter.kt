package woowacourse.movie.presenter.movies

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.theater.MovieScreeningInfoByTheater
import woowacourse.movie.model.theater.MovieScreeningInfoByTheaters

class MoviesPresenter(
    private val view: MoviesContracts.View,
) : MoviesContracts.Presenter {
    private lateinit var movieScreeningInfoByTheaters: List<MovieScreeningInfoByTheater>
    override fun initView() {
        view.showMovies(Movie.values)
    }

    override fun onTheaterRequested(movieId: Long) {
        movieScreeningInfoByTheaters = MovieScreeningInfoByTheater.values
        MovieScreeningInfoByTheater.findMovieScreeningInfo(movieScreeningInfoByTheaters, movieId)
        view.showTheaters(MovieScreeningInfoByTheaters(MovieScreeningInfoByTheater.values))
    }

    override fun onAdvertisementRequested(url: String) {
        view.showAdvertisement(url)
    }
}
