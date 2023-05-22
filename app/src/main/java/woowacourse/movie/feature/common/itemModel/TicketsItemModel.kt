package woowacourse.movie.feature.common.itemModel

import woowacourse.movie.model.TicketsState

class TicketsItemModel(
    val ticketsState: TicketsState,
    val onClick: (ticketsState: TicketsState) -> Unit
) : ItemModel {
    override val viewType: ViewType = ViewType.TICKETS
}
