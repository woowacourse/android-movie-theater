package woowacourse.movie.fragment.reservationlist

import woowacourse.movie.view.model.ReservationUiModel

data class ReservationItemModel(
    val reservationUiModel: ReservationUiModel,
    val onClick: (ReservationUiModel) -> Unit
)
