package woowacourse.movie.presenter.home

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.theater.TheaterMovieSchedules
import woowacourse.movie.view.home.MovieType

class HomePresenter(
    private val view: HomeContracts.View,
) : HomeContracts.Presenter {
    private lateinit var theaterMovieSchedules: TheaterMovieSchedules

    override fun updateView() {
        view.showMovies(
            Movie.values.mapIndexed { i, v ->
                if (i % 10 == 0) {
                    MovieType.AdvertisementItem("https://www.woowacourse.io/")
                } else {
                    MovieType.MovieItem(v)
                }
            },
        )
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

    override fun onAdvertisementRequested(url: String) {
        view.showAdvertisement(url)
    }
}
