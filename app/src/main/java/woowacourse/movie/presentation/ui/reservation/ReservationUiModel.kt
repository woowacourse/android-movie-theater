package woowacourse.movie.presentation.ui.reservation

import woowacourse.movie.domain.model.Reservation

data class ReservationUiModel(
    val reservation: Reservation? = null,
    val theaterName: String = "",
)
