package woowacourse.movie.presentation.ui.reservation

import woowacourse.movie.domain.db.reservationdb.ReservationEntity

data class ReservationModel(
    val reservation: ReservationEntity? = null,
    val theaterName: String = "",
)
