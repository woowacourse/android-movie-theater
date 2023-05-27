package woowacourse.movie.activity.reservationresult

import woowacourse.movie.view.data.ReservationViewData

class ReservationResultPresenter(val view: ReservationResultContract.View) :
    ReservationResultContract.Presenter {
    override fun setUpReservation(
        reservationViewData: ReservationViewData,
    ) {
        view.setUpReservation(reservationViewData)
    }
}
