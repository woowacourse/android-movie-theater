package woowacourse.movie.data

import java.io.Serializable

data class ReservationViewData(
    val movie: MovieViewData,
    val reservationDetail: ReservationDetailViewData,
    val seats: SeatsViewData,
    val price: PriceViewData
) : Serializable {
    companion object {
        const val RESERVATION_EXTRA_NAME = "reservation"
    }
}
