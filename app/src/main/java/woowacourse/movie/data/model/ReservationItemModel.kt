package woowacourse.movie.data.model

import woowacourse.movie.data.model.uimodel.ReservationUiModel

data class ReservationItemModel(
    val reservationUiModel: ReservationUiModel,
    val onClick: (ReservationUiModel) -> Unit
)
