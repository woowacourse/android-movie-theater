package woowacourse.movie.ui.seat.presenter

import woowacourse.movie.domain.model.movie.Headcount
import woowacourse.movie.domain.model.theater.Seat
import woowacourse.movie.domain.model.theater.Seats
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.ui.seat.contract.BookingSeatContract

class BookingSeatPresenter(
    private val bookingSeatView: BookingSeatContract.View,
) : BookingSeatContract.Presenter {
    private lateinit var headcount: Headcount
    private lateinit var movieTitle: String
    private lateinit var theater: Theater
    private val seats: Seats = Seats()

    fun updateViews() {
        refreshMovieTitle()
        refreshTotalPrice()
        refreshConfirmButton()
    }

    override fun loadState(
        theater: Theater,
        headcount: Headcount,
        title: String
    ) {
        this.theater = theater
        this.headcount = headcount
        this.movieTitle = title
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

    override fun completeBookingSeat() {
        bookingSeatView.startBookingCompleteActivity(movieTitle, headcount, seats, theater)
    }
}
