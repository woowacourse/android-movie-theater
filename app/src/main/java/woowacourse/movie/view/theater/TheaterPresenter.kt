package woowacourse.movie.view.theater

import woowacourse.movie.R
import woowacourse.movie.model.Movie
import woowacourse.movie.model.MovieDao
import woowacourse.movie.model.Theater
import woowacourse.movie.model.TheaterUIModel

class TheaterPresenter(
    val view: TheaterContract.View,
    val movieDao: MovieDao,
) : TheaterContract.Presenter {
    override fun fetchTheaters(getMovie: () -> Movie?) {
        val movie = getMovie()
        if (movie == null) {
            view.showErrorMessage(R.string.bottom_sheet_dialog_error_movie_load_failed)
            view.dismissView()
            return
        }
        val theaterUIModels =
            movieDao
                .getTheaterNames()
                .map {
                    val theater = Theater(it, movieDao.getMovies(it), movieDao)
                    TheaterUIModel(it, movie, theater.getTotalTimeSlotCount(movie))
                }
        view.showTheaters(theaterUIModels)
    }

    override fun theaterSelected(theaterUIModel: TheaterUIModel) {
        view.navigateToReservation(theaterUIModel)
    }
}
