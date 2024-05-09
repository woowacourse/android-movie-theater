package woowacourse.movie.list.presenter

import woowacourse.movie.list.contract.ReservationHistoryContract
import woowacourse.movie.ticket.model.TicketDataResource

class ReservationHistoryPresenter(
    override val view: ReservationHistoryContract.View
) : ReservationHistoryContract.Presenter {
    private val ticket = TicketDataResource.dbTicket
}
