package woowacourse.movie.view.data

import java.io.Serializable

data class ReservationViewData(
    val movie: MovieViewData,
    val reservationDetail: ReservationDetailViewData
) : Serializable
