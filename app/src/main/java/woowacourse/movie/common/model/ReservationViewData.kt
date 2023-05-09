package woowacourse.movie.common.model

import java.io.Serializable

data class ReservationViewData(
    val movie: MovieViewData,
    val reservationDetail: ReservationDetailViewData,
    val seats: SeatsViewData,
    val price: PriceViewData,
    val theaterName: String
) : Serializable {
    companion object {
        const val RESERVATION_EXTRA_NAME = "reservation"
    }
}
