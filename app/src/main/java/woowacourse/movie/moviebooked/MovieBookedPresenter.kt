package woowacourse.movie.moviebooked

import woowacourse.movie.domain.ReservationInfo

class MovieBookedPresenter(
    private val view: MovieBookedContract.View,
) : MovieBookedContract.Presenter {
    override fun loadReservationInfo(reservationInfo: ReservationInfo) {
        view.showReservationInfo(reservationInfo)
    }
}
