package woowacourse.movie.ui.itemModel

import woowacourse.movie.model.TicketsState

class TicketsItemModel(
    val ticketsState: TicketsState,
    override val onClick: (position: Int) -> Unit
) : ItemModel {
    override val viewType: ViewType = ViewType.MOVIE
}
