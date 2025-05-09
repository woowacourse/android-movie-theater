package woowacourse.movie.view.history

import woowacourse.movie.view.history.adapter.TicketAdapter

class TickAdapterActionHandler(
    private val view: BookingHistoryContract.View,
) : TicketAdapter.Handler {
    override fun onClick(ticketId: Long) {
        view.moveToTicketDetail(ticketId)
    }
}
