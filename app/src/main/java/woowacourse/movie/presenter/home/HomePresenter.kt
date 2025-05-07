package woowacourse.movie.presenter.home

import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.theater.TheaterMovieSchedules
import woowacourse.movie.view.home.MovieType

class HomePresenter(
    private val view: HomeContracts.View,
) : HomeContracts.Presenter {
    override fun updateView() {
        view.showMovies(
            Movie.values.flatMapIndexed { i, v ->
                if (i % 10 == 0) {
                    listOf(
                        MovieType.AdvertisementItem("https://www.woowacourse.io/"),
                        MovieType.MovieItem(v),
                    )
                } else {
                    listOf(MovieType.MovieItem(v))
                }
            },
        )
    }

    override fun updateTheater(movieId: Long) {
        val filteredSchedules = TheaterMovieSchedules().findTheaterMovieSchedulesById(movieId)
        view.showTheaters(filteredSchedules)
    }

    override fun updateAdvertisement(url: String) {
        view.showAdvertisement(url)
    }
}
