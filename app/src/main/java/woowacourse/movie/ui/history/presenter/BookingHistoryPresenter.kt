package woowacourse.movie.ui.history.presenter

import kotlin.concurrent.thread
import woowacourse.movie.data.BookedTicketDao
import woowacourse.movie.data.BookedTicketDatabase
import woowacourse.movie.data.BookedTicketEntity
import woowacourse.movie.data.toBookedTicket
import woowacourse.movie.ui.history.contract.BookingHistoryContract

class BookingHistoryPresenter(
    private val view: BookingHistoryContract.View,
) : BookingHistoryContract.Presenter {
    override fun loadBookingHistories(bookedTicketDatabase: BookedTicketDatabase) {
        val dao: BookedTicketDao = bookedTicketDatabase.bookedTicketDao()
        thread {
            val bookingHistories = dao.findAll()
            view.showHistories(bookingHistories)
        }
    }

    override fun loadBookedTicket(bookedTicketEntity: BookedTicketEntity) {
        view.moveToBookedTicket(bookedTicketEntity.toBookedTicket())
    }
}
