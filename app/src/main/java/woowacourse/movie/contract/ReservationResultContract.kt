package woowacourse.movie.contract

import woowacourse.movie.data.MovieViewData
import woowacourse.movie.data.PriceViewData
import woowacourse.movie.data.ReservationDetailViewData
import woowacourse.movie.data.ReservationViewData
import woowacourse.movie.data.SeatsViewData

interface ReservationResultContract {
    interface View {
        val presenter: Presenter
        fun setMovieData(movie: MovieViewData)
        fun setReservationDetailData(reservationDetail: ReservationDetailViewData)
        fun setPriceData(price: PriceViewData)
        fun setSeatData(reservationDetail: ReservationDetailViewData, seats: SeatsViewData)
    }

    interface Presenter {
        val view: View
        fun initActivity(reservation: ReservationViewData)
    }
}
