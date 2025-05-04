package woowacourse.movie.presentation.result

import woowacourse.movie.domain.model.Ticket

interface BookingResultContract {
    interface View {
        fun showTicketInfo(ticket: Ticket)
    }

    interface Presenter {
        fun loadBookingResult()
    }
}
