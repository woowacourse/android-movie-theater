package woowacourse.movie.view.theater

import woowacourse.movie.model.Movie
import woowacourse.movie.model.MovieDao
import woowacourse.movie.model.Theater
import woowacourse.movie.model.TheaterUIModel

class TheaterPresenter(
    val view: TheaterContract.View,
    val movie: Movie,
) : TheaterContract.Presenter {
    private val movieDao: MovieDao by lazy { MovieDao() }

    override fun fetchTheaters() {
        val theaterUIModels =
            movieDao
                .getTheaterNames()
                .map {
                    val theater = Theater(it, movieDao.getMovies(it))
                    TheaterUIModel(it, movie, theater.getTotalTimeSlotCount(movie))
                }
        view.showTheaters(theaterUIModels)
    }

    override fun theaterSelected(theaterUIModel: TheaterUIModel) {
        view.navigateToReservation(theaterUIModel)
    }
}
