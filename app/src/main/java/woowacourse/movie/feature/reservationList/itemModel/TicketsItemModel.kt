package woowacourse.movie.feature.reservationList.itemModel

import woowacourse.movie.model.TicketsState

data class TicketsItemModel(
    val ticketsState: TicketsState,
    val onClick: (ticketsState: TicketsState) -> Unit
)
