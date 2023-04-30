package woowacourse.movie.feature.reservationList.itemModel

import woowacourse.movie.feature.common.ViewType
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.model.TicketsState

class TicketsItemModel(
    val ticketsState: TicketsState,
    override val onClick: (position: Int) -> Unit
) : ItemModel {
    override val viewType: ViewType = ViewType.MOVIE
}
