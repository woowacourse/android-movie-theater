package woowacourse.movie.presenter.home

import woowacourse.movie.db.advertisement.AdvertisementDao
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.model.movie.Movie

class ReservationHomePresenter(
    private val view: ReservationHomeContract.View,
) : ReservationHomeContract.Presenter {
    override fun loadMovies() {
        view.showMovieData(
            ScreeningDao().findAll(),
            AdvertisementDao().findAll()
        )
    }

    override fun loadMovie(movie: Movie) {
        val movieId = movie.id
        view.navigateToDetail(movieId)
    }
}
