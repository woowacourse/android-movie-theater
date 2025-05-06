package woowacourse.movie.presentation.common.model

import woowacourse.movie.domain.model.reservation.ReservationCount

class ReservationCountUiModel(
    val count: Int,
    val isMin: Boolean,
    val isMax: Boolean,
)

fun ReservationCount.toUiModel(max: Int) =
    ReservationCountUiModel(
        count = value,
        isMin = isMin(),
        isMax = isMax(max),
    )
