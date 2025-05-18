package woowacourse.movie.ui.complete

import woowacourse.movie.domain.model.BookedTicket

interface BookingCompleteContract {
    interface Presenter {
        fun loadBookedTicket(bookedTicketId: Long)
    }

    interface View {
        fun showBookedTicket(bookedTicket: BookedTicket)
    }
}
