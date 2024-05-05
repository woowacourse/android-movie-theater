package woowacourse.movie.feature.home

import woowacourse.movie.db.advertisement.AdvertisementDao
import woowacourse.movie.db.screening.ScreeningDao

class ReservationHomePresenter(
    private val view: ReservationHomeContract.View,
    private val screeningDao: ScreeningDao,
    private val advertisementDao: AdvertisementDao,
) : ReservationHomeContract.Presenter {
    override fun loadMovieCatalog() {
        val movies = screeningDao.findAll()
        val advertisements = advertisementDao.findAll()
        view.showMovieCatalog(movies, advertisements)
    }

    override fun sendMovieIdToTheaterSelection(movieId: Int) {
        view.navigateToTheaterSelection(movieId)
    }
}
