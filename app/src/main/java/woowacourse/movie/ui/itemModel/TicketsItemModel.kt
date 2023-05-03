package woowacourse.movie.ui.itemModel

import woowacourse.movie.model.TicketsState

class TicketsItemModel(
    val ticketsState: TicketsState
) : ItemModel {
    companion object {
        const val type: Int = 3
    }
}
