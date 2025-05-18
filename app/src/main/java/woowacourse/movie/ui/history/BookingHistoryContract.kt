package woowacourse.movie.ui.history

import woowacourse.movie.domain.model.BookedTicket

interface BookingHistoryContract {
    interface Presenter {
        fun loadBookingHistories()

        fun loadBookedTicket(bookedTicket: BookedTicket)
    }

    interface View {
        fun showHistories(bookedTickets: List<BookedTicket>)

        fun moveToBookedTicket(bookedTicket: BookedTicket)
    }
}
