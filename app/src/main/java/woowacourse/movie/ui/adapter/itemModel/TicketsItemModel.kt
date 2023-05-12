package woowacourse.movie.ui.adapter.itemModel

import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.adapter.itemModel.ItemModel.Companion.TYPE_TICKETS

class TicketsItemModel(
    val ticketsState: TicketsState
) : ItemModel {
    override val viewType: Int
        get() = TYPE_TICKETS
}
