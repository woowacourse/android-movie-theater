package woowacourse.movie.reservationResult

import woowacourse.movie.common.model.MovieViewData
import woowacourse.movie.common.model.PriceViewData
import woowacourse.movie.common.model.ReservationDetailViewData
import woowacourse.movie.common.model.SeatsViewData

interface ReservationResultContract {
    interface View {
        val presenter: Presenter
        fun setMovieData(movie: MovieViewData)
        fun setReservationDetailData(reservationDetail: ReservationDetailViewData)
        fun setPriceData(price: PriceViewData)
        fun setSeatData(reservationDetail: ReservationDetailViewData, seats: SeatsViewData, theaterName: String)
    }

    interface Presenter {
        val view: View
    }
}
