package woowacourse.movie.presenter.home

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.theater.TheaterMovieSchedules

class HomePresenter(
    private val view: HomeContracts.View,
) : HomeContracts.Presenter {
    override fun initView() {
        view.showMovies(Movie.values)
    }

    override fun requestTheaters(movieId: Long) {
        val theaterMovieSchedules = TheaterMovieSchedules()
        val selectedMovieSchedules =
            TheaterMovieSchedules(
                theaterMovieSchedules.findTheaterMovieSchedulesById(
                    movieId,
                ),
            )
        view.showTheaters(selectedMovieSchedules)
    }

    override fun requestAdvertisement(url: String) {
        view.showAdvertisement(url)
    }
}
