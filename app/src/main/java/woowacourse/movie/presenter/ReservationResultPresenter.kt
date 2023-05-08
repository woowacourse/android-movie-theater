package woowacourse.movie.presenter

import woowacourse.movie.contract.ReservationResultContract
import woowacourse.movie.data.ReservationViewData

class ReservationResultPresenter(
    override val view: ReservationResultContract.View,
    reservationViewData: ReservationViewData
) : ReservationResultContract.Presenter {
    init {
        view.setMovieData(reservationViewData.movie)
        view.setReservationDetailData(reservationViewData.reservationDetail)
        view.setSeatData(
            reservationViewData.reservationDetail,
            reservationViewData.seats,
            reservationViewData.theaterName
        )
        view.setPriceData(reservationViewData.price)
    }
}
