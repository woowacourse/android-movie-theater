package woowacourse.movie.ui.history.contract

import woowacourse.movie.domain.model.theater.BookedTicket

interface BookingHistoryContract {
    interface Presenter {
        fun loadBookedTickets()
    }

    interface View {
        fun setBookedTicketItems(bookedTickets: List<BookedTicket>)
    }
}
