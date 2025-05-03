package woowacourse.movie.ui.complete.contract

import woowacourse.movie.domain.model.theater.BookedTicket

interface BookingCompleteContract {
    interface Presenter {
        fun loadBookedTicket(): BookedTicket

        fun refreshBookedTicketDisplay()

        fun refreshTicketPrice()
    }

    interface View {
        fun getBookedTicket(): BookedTicket?

        fun setBookedTicket(bookedTicket: BookedTicket)

        fun setBookedTicketPrice(price: Int)
    }
}
