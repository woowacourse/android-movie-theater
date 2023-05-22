package woowacourse.movie.feature.reservation.list

import woowacourse.movie.data.TicketsRepository
import woowacourse.movie.feature.common.itemModel.ItemModel

class ReservationListPresenter(
    val view: ReservationListContract.View
) : ReservationListContract.Presenter {

    override fun setListItems() {
        val items: List<ItemModel> = getTickets()
        view.setItems(items)
    }

    private fun getTickets(): List<ItemModel> {
        return TicketsRepository.allTickets().map { ticketState ->
            ticketState.convertToItemModel { view.navigateTicketsConfirm(ticketState) }
        }
    }
}
