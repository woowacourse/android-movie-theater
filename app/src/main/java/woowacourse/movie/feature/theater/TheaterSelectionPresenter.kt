package woowacourse.movie.feature.theater

import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.movie.Movie.Companion.DEFAULT_MOVIE_ID

class TheaterSelectionPresenter(
    private val view: TheaterSelectionContract.View,
    private val theaterDao: TheaterDao,
    private val movieId: Int,
) : TheaterSelectionContract.Presenter {
    override fun loadTheater() {
        if (movieId != DEFAULT_MOVIE_ID) {
            loadTheaterInformation()
        } else {
            handleUndeliveredMovieId()
        }
    }

    override fun sendTheaterInfoToReservation(theaterId: Int) {
        view.navigateToReservation(movieId, theaterId)
    }

    private fun loadTheaterInformation() {
        val theaters = theaterDao.findTheaterByMovieId(movieId)
        val screeningCounts =
            theaters.map {
                theaterDao.findScreeningTimesByMovieId(it.theaterId, movieId).size
            }.toList()
        view.showTheaters(theaters, screeningCounts)
    }

    private fun handleUndeliveredMovieId() {
        view.showErrorSnackBar()
    }
}
