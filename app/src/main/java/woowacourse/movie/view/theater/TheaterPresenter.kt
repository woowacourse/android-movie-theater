package woowacourse.movie.view.theater

import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieDao
import woowacourse.movie.domain.model.TheaterUIModel

class TheaterPresenter(
    val view: TheaterContract.View,
) : TheaterContract.Presenter {
    private val movieDao: MovieDao by lazy { MovieDao() }

    override fun fetchTheaters(movie: Movie) {
//        val theaterUIModels =
//            movieDao
//                .getTheaterNames()
//                .map {
//                    val theater = Theater(it, movieDao.getMovies(it))
//                    TheaterUIModel(it, movie, theater.getTotalTimeSlotCount(movie))
//                }
//        view.showTheaters(theaterUIModels)
    }

    override fun theaterSelected(theaterUIModel: TheaterUIModel) {
        view.navigateToReservation(theaterUIModel)
    }
}
