package woowacourse.movie.reservationResult

import woowacourse.movie.common.model.ReservationViewData

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
