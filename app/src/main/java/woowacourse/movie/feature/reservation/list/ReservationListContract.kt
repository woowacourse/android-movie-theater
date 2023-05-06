package woowacourse.movie.feature.reservation.list

import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.model.TicketsState

interface ReservationListContract {
    interface View {
        fun setItems(items: List<ItemModel>)
        fun navigateTicketsConfirm(tickets: TicketsState)
    }

    interface Presenter {
        fun setListItems()
    }
}
