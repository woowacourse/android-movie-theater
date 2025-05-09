package woowacourse.movie.ui.history.contract

import woowacourse.movie.data.BookedTicketDatabase
import woowacourse.movie.data.BookedTicketEntity
import woowacourse.movie.domain.model.BookedTicket

interface BookingHistoryContract {
    interface Presenter {
        fun loadBookingHistories(bookedTicketDatabase: BookedTicketDatabase)

        fun loadBookedTicket(bookedTicketEntity: BookedTicketEntity)
    }

    interface View {
        fun showHistories(bookingHistories: List<BookedTicketEntity>)

        fun moveToBookedTicket(bookedTicket: BookedTicket)
    }
}
