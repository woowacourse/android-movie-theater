package woowacourse.movie.feature.reservationList.itemModel

import woowacourse.movie.feature.common.CommonViewType
import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.model.TicketsState

data class TicketsItemModel(
    val ticketsState: TicketsState,
    val onClick: (ticketsState: TicketsState) -> Unit
) : CommonItemModel {
    override val viewType: CommonViewType = CommonViewType.RESERVATION
}
