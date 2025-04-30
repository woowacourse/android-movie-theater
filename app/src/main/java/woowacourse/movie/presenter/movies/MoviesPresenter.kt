package woowacourse.movie.presenter.movies

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.theater.MovieScreeningInfoByTheaters

class MoviesPresenter(
    private val view: MoviesContracts.View,
) : MoviesContracts.Presenter {
    private lateinit var movieScreeningInfoByTheaters: MovieScreeningInfoByTheaters

    override fun initView() {
        view.showMovies(Movie.values)
    }

    override fun onTheaterRequested(movieId: Long) {
        movieScreeningInfoByTheaters = MovieScreeningInfoByTheaters()
        val movieScreeningInfoByTheaters =
            MovieScreeningInfoByTheaters(
                movieScreeningInfoByTheaters.findMovieScreeningInfoByTheaterById(
                    movieId,
                ),
            )
        view.showTheaters(movieScreeningInfoByTheaters)
    }

    override fun onAdvertisementRequested(url: String) {
        view.showAdvertisement(url)
    }
}
