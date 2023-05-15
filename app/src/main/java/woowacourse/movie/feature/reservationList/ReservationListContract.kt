package woowacourse.movie.feature.reservationList

import woowacourse.movie.feature.reservationList.itemModel.TicketsItemModel
import woowacourse.movie.model.TicketsState

interface ReservationListContract {
    interface View {
        fun navigateReservationConfirm(ticketsState: TicketsState)
        fun updateItems(items: List<TicketsItemModel>)
        fun errorLoadReservationTicketsData()
    }

    interface Presenter {
        fun loadTicketsItems()
    }
}
