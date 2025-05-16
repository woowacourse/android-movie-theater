package woowacourse.movie.ui.seat.presenter

import woowacourse.movie.data.mapper.BookedTicketMapper
import woowacourse.movie.data.repository.BookedTicketRepository
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
    private val bookedTicketRepository: BookedTicketRepository,
) : BookingSeatContract.Presenter {
    private lateinit var headcount: Headcount
    private lateinit var movieTitle: String
    private lateinit var theater: Theater
    private lateinit var bookedDateTime: LocalDateTime
    private var notificationSetting: Boolean = false
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
        notificationSetting: Boolean,
    ) {
        this.theater = theater
        this.headcount = headcount
        this.movieTitle = title
        this.bookedDateTime = bookedDateTime
        this.notificationSetting = notificationSetting
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
            bookedTicketRepository
                .insertBookedTicket(BookedTicketMapper.toEntity(bookedTicket))
        }
    }

    override fun completeBookingSeat() {
        val bookedTicket = BookedTicket(movieTitle, headcount, bookedDateTime, seats, theater.name)
        bookingSeatView.startBookingCompleteActivity(bookedTicket)
    }

    override fun postNotification() {
        if (notificationSetting == false) return
        val bookedTicket = BookedTicket(movieTitle, headcount, bookedDateTime, seats, theater.name)
        bookingSeatView.setAlarmManager(bookedTicket)
    }
}
