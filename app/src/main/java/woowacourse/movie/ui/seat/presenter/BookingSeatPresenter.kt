package woowacourse.movie.ui.seat.presenter

import woowacourse.movie.data.database.AppDatabase
import woowacourse.movie.data.entity.BookedTicketEntity
import woowacourse.movie.domain.model.movie.Headcount
import woowacourse.movie.domain.model.theater.BookedTicket
import woowacourse.movie.domain.model.theater.Seat
import woowacourse.movie.domain.model.theater.Seats
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.ui.seat.contract.BookingSeatContract
import java.time.LocalDateTime
import kotlin.concurrent.thread

class BookingSeatPresenter(
    private val bookingSeatView: BookingSeatContract.View,
    private val appDatabase: AppDatabase,
) : BookingSeatContract.Presenter {
    private lateinit var headcount: Headcount
    private lateinit var movieTitle: String
    private lateinit var theater: Theater
    private lateinit var bookedDateTime: LocalDateTime
    private val seats: Seats = Seats()

    fun updateViews() {
        refreshMovieTitle()
        refreshTotalPrice()
        refreshConfirmButton()
    }

    override fun loadState(
        theater: Theater,
        headcount: Headcount,
        title: String,
        bookedDateTime: LocalDateTime,
    ) {
        this.theater = theater
        this.headcount = headcount
        this.movieTitle = title
        this.bookedDateTime = bookedDateTime
    }

    override fun refreshTotalPrice() {
        bookingSeatView.setTotalPrice(seats.totalPrice())
    }

    override fun refreshMovieTitle() {
        bookingSeatView.setMovieTitle(movieTitle)
    }

    override fun selectSeat(seat: Seat) {
        if (seats.contains(seat)) {
            seats.remove(seat)
            bookingSeatView.toggleSeat(seat, false)
        } else if (seats.size < headcount.count) {
            seats.add(seat)
            bookingSeatView.toggleSeat(seat, true)
        }
        refreshTotalPrice()
        refreshConfirmButton()
    }

    override fun refreshConfirmButton() {
        if (seats.size != headcount.count) {
            bookingSeatView.setConfirmButton(false)
        } else {
            bookingSeatView.setConfirmButton(true)
        }
    }

    override fun insertBookedTicket() {
        thread {
            val bookedTicket =
                BookedTicket(movieTitle, headcount, bookedDateTime, seats, theater.name)
            appDatabase.bookedTicketDao().insertBookedTicket(bookedTicket.toEntity())
        }
    }

    override fun completeBookingSeat() {
        val bookedTicket = BookedTicket(movieTitle, headcount, bookedDateTime, seats, theater.name)
        bookingSeatView.startBookingCompleteActivity(bookedTicket)
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
