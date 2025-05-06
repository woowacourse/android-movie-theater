package woowacourse.movie.ui.seat

import woowacourse.movie.domain.model.Headcount
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieSchedule
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.sample.DUMMY_MOVIES

class BookingSeatPresenter(
    private val bookingSeatView: BookingSeatContract.View,
) : BookingSeatContract.Presenter {
    private lateinit var movie: Movie
    private lateinit var headcount: Headcount
    private lateinit var theaterName: String
    private lateinit var movieSchedule: MovieSchedule
    private lateinit var seats: Seats

    override fun loadBookingSeatInfo(
        movieId: Long,
        movieSchedule: MovieSchedule,
        headcount: Headcount,
        theaterName: String,
    ) {
        movie = DUMMY_MOVIES[movieId]!!
        this.headcount = headcount
        this.movieSchedule = movieSchedule
        this.theaterName = theaterName
        seats = movieSchedule.seats

        bookingSeatView.showMovieTitle(movie.title)
        bookingSeatView.showTotalPrice(seats.totalPrice())
        bookingSeatView.showConfirmButton(seats.isSeatSelectionComplete(headcount))
    }

    override fun updateSeat(seatTag: String) {
        val targetSeat = Seat.fromSeatTag(seatTag)
        val isReserved = seats.isReservedSeat(targetSeat)

        if (seats.isSeatSelectionComplete(headcount)) {
            allSeatSelectionByIsReserved(isReserved, targetSeat)
            return
        }

        if (!isReserved) {
            seats.reserve(targetSeat)
        } else {
            seats.cancelReserve(targetSeat)
        }

        bookingSeatView.showSeatView(targetSeat, !isReserved)
        bookingSeatView.showTotalPrice(seats.totalPrice())
    }

    override fun updateConfirmButton() {
        bookingSeatView.showConfirmButton(seats.isSeatSelectionComplete(headcount))
    }

    override fun loadBookedTicket() {
        bookingSeatView.moveToBookedTicket(
            theaterName = theaterName,
            movieTitle = movie.title,
            schedule = movieSchedule,
            headcount = headcount,
        )
    }

    private fun allSeatSelectionByIsReserved(
        isReserved: Boolean,
        targetSeat: Seat,
    ) {
        if (isReserved) {
            seats.cancelReserve(targetSeat)
            bookingSeatView.showSeatView(targetSeat, false)
            bookingSeatView.showTotalPrice(seats.totalPrice())
        }
    }
}
