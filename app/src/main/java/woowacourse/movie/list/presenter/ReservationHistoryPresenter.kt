package woowacourse.movie.list.presenter

import woowacourse.movie.common.CommonDataSource
import woowacourse.movie.list.contract.ReservationHistoryContract

class ReservationHistoryPresenter(
    override val view: ReservationHistoryContract.View
) : ReservationHistoryContract.Presenter {
    private val tickets = CommonDataSource.dbTickets

    override fun setReservationHistoryInfo() {
        view.showReservationHistoryList()
    }

    override fun updateReservationHistoryInfo() {
        view.updateItems(tickets)
    }

    override fun setReservationHistoryAdapter() {
        view.linkReservationHistoryAdapter(tickets)
    }
}
