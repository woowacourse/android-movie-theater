package woowacourse.movie.ui.history.presenter

import woowacourse.movie.data.database.AppDatabase
import woowacourse.movie.data.entity.BookedTicketEntity
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
            val bookedTickets = appDatabase.bookedTicketDao().getAll().map { it.toModel() }
            this.bookedTickets = bookedTickets
            bookingHistoryView.setBookedTicketItems(bookedTickets)
        }
    }
}

private fun BookedTicketEntity.toModel(): BookedTicket =
    BookedTicket(
        movieName,
        headcount,
        dateTime,
        seats,
        theaterName,
    )
