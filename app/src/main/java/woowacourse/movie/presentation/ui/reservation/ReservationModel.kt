package woowacourse.movie.presentation.ui.reservation

import woowacourse.movie.domain.model.Reservation

data class ReservationModel(
    val reservation: Reservation? = null,
    val theaterName: String = "",
)
