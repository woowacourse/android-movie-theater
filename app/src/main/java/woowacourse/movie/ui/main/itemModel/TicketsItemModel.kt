package woowacourse.movie.ui.main.itemModel

import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.main.ViewType

class TicketsItemModel(
    val ticketsState: TicketsState,
    override val onClick: (position: Int) -> Unit
) : ItemModel {
    override val viewType: ViewType = ViewType.MOVIE
}
