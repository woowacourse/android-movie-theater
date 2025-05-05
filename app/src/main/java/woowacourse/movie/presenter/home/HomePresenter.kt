package woowacourse.movie.presenter.home

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.theater.TheaterMovieSchedules

class HomePresenter(
    private val view: HomeContracts.View,
) : HomeContracts.Presenter {
    private lateinit var theaterMovieSchedules: TheaterMovieSchedules

    override fun initView() {
        view.showMovies(Movie.values)
    }

    override fun onTheaterRequested(movieId: Long) {
        theaterMovieSchedules = TheaterMovieSchedules()
        val theaterMovieSchedules =
            TheaterMovieSchedules(
                theaterMovieSchedules.findTheaterMovieSchedulesById(
                    movieId,
                ),
            )
        view.showTheaters(theaterMovieSchedules)
    }

    override fun requestAdvertisement(url: String) {
        view.showAdvertisement(url)
    }
}
