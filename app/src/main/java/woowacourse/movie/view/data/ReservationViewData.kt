package woowacourse.movie.view.data

import java.io.Serializable

data class ReservationViewData(
    val movie: MovieViewData,
    val reservationDetail: ReservationDetailViewData,
    val seats: SeatsViewData,
    val price: PriceViewData
) : Serializable
