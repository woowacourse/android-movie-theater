package woowacourse.movie.feature.theater

import woowacourse.movie.db.theater.TheaterDao

class TheaterSelectionPresenter(
    private val view: TheaterSelectionContract.View,
    private val theaterDao: TheaterDao,
    private val movieId: Int? = null,
) : TheaterSelectionContract.Presenter {
    override fun loadTheater() {
        if (isValidMovieId()) {
            loadTheaterInformation()
        } else {
            handleUndeliveredMovieId()
        }
    }

    override fun sendTheaterInfoToReservation(theaterId: Int) {
        movieId?.let {
            view.navigateToReservation(movieId, theaterId)
        }
    }

    private fun isValidMovieId(): Boolean {
        return movieId != null
    }

    private fun loadTheaterInformation() {
        movieId?.let {
            val theaters = theaterDao.findTheaterByMovieId(movieId)
            val screeningCounts =
                theaters.map {
                    theaterDao.findScreeningTimesByMovieId(it.theaterId, movieId).size
                }.toList()
            view.showTheaters(theaters, screeningCounts)
        }
    }

    private fun handleUndeliveredMovieId() {
        view.showErrorSnackBar()
    }
}
