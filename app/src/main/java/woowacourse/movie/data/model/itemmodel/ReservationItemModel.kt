package woowacourse.movie.data.model.itemmodel

import woowacourse.movie.data.model.uimodel.ReservationUIModel

data class ReservationItemModel(
    val reservationUiModel: ReservationUIModel,
    val onClick: (ReservationUIModel) -> Unit
)
