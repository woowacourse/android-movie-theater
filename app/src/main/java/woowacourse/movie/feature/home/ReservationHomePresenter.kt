package woowacourse.movie.feature.home

class ReservationHomePresenter(
    private val view: ReservationHomeContract.View,
) : ReservationHomeContract.Presenter {
    override fun loadMovie(movieId: Int) {
        view.navigateToDetail(movieId)
    }
}
