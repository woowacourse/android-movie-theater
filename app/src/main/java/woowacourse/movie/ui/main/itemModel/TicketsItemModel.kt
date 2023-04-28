package woowacourse.movie.ui.main.itemModel

import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.main.ViewType

class TicketsItemModel(
    val ticketsState: TicketsState
) : ItemModel {
    override val viewType: ViewType = ViewType.MOVIE
}
