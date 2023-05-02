package woowacourse.movie.presenter

import woowacourse.movie.contract.ReservationResultContract
import woowacourse.movie.data.ReservationViewData

class ReservationResultPresenter(override val view: ReservationResultContract.View) :
    ReservationResultContract.Presenter {
    override fun initActivity(reservation: ReservationViewData) {
        view.setMovieData(reservation.movie)
        view.setReservationDetailData(reservation.reservationDetail)
        view.setSeatData(reservation.reservationDetail, reservation.seats)
        view.setPriceData(reservation.price)
    }
}
