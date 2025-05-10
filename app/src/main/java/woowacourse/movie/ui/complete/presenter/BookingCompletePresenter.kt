package woowacourse.movie.ui.complete.presenter

import woowacourse.movie.data.database.AppDatabase
import woowacourse.movie.data.entity.BookedTicketEntity
import woowacourse.movie.domain.model.theater.BookedTicket
import woowacourse.movie.ui.complete.contract.BookingCompleteContract
import kotlin.concurrent.thread

class BookingCompletePresenter(
    private val bookingCompleteView: BookingCompleteContract.View,
    private val appDatabase: AppDatabase,
) : BookingCompleteContract.Presenter {
    private lateinit var bookedTicket: BookedTicket

    fun updateViews() {
        refreshBookedTicketDisplay()
        refreshTicketPrice()
    }

    override fun loadBookedTicket(bookedTicket: BookedTicket) {
        this.bookedTicket = bookedTicket
    }

    override fun insertBookedTicket() {
        thread {
            appDatabase.bookedTicketDao().insertBookedTicket(bookedTicket.toEntity())
        }
    }

    override fun refreshTicketPrice() {
        val price = bookedTicket.totalPrice()
        bookingCompleteView.setBookedTicketPrice(price)
    }

    override fun refreshBookedTicketDisplay() {
        bookingCompleteView.setBookedTicket(bookedTicket)
    }
}

private fun BookedTicket.toEntity(uid: Int = 0): BookedTicketEntity =
    BookedTicketEntity(
        uid = uid,
        movieName = movieName,
        headcount = headcount,
        dateTime = dateTime,
        seats = seats,
        theaterName = theaterName,
    )
