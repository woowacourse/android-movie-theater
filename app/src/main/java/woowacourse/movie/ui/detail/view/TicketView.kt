package woowacourse.movie.ui.detail.view

interface TicketView {
    fun initClickListener(ticketReserveListener: TicketReserveListener)

    fun updateTicketCount(count: Int)

    fun ticketCount(): Int
}
