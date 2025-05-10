package woowacourse.movie.view.history

import woowacourse.movie.domain.model.ticket.Ticket

interface BookingHistoryEventHandler {
    fun onBookingSelected(ticket: Ticket)
}
