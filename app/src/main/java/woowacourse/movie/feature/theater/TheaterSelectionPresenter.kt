package woowacourse.movie.feature.theater

import woowacourse.movie.db.theater.TheaterDao

class TheaterSelectionPresenter(
    private val view: TheaterSelectionContract.View,
    private val theaterDao: TheaterDao,
    private val movieId: Int,
) : TheaterSelectionContract.Presenter {
    override fun loadTheater() {
        val theaters = theaterDao.findTheaterByMovieId(movieId)
        val screeningCounts =
            theaters.map {
                theaterDao.findScreeningTimesByMovieId(it.theaterId, movieId).size
            }.toList()
        view.showTheaters(theaters, screeningCounts)
    }

    override fun sendTheaterInfoToReservation(theaterId: Int) {
        view.navigateToReservation(movieId, theaterId)
    }
}
