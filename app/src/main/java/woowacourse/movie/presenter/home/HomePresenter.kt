package woowacourse.movie.presenter.home

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.theater.MovieScreeningInfoByTheaters

class HomePresenter(
    private val view: HomeContracts.View,
) : HomeContracts.Presenter {
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
