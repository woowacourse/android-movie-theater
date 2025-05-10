package woowacourse.movie.ui.history.presenter

import woowacourse.movie.data.database.AppDatabase
import woowacourse.movie.data.mapper.BookedTicketMapper
import woowacourse.movie.domain.model.theater.BookedTicket
import woowacourse.movie.ui.history.contract.BookingHistoryContract
import kotlin.concurrent.thread

class BookingHistoryPresenter(
    val bookingHistoryView: BookingHistoryContract.View,
    val appDatabase: AppDatabase,
) : BookingHistoryContract.Presenter {
    private lateinit var bookedTickets: List<BookedTicket>

    override fun loadBookedTickets() {
        thread {
            val bookedTickets =
                appDatabase.bookedTicketDao().getAll().map { BookedTicketMapper.toModel(it) }
            this.bookedTickets = bookedTickets
            bookingHistoryView.setBookedTicketItems(bookedTickets)
        }
    }
}
