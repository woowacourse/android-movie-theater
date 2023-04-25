package woowacourse.movie.ui.main.itemModel

import woowacourse.movie.model.ReservationState
import woowacourse.movie.ui.main.ViewType

class ReservationItemModel(
    val reservationState: ReservationState,
    override val onClick: (position: Int) -> Unit
) : ItemModel {
    override val viewType: ViewType = ViewType.MOVIE
}
