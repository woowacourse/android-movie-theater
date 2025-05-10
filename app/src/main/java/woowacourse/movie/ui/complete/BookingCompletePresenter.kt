package woowacourse.movie.ui.complete

import woowacourse.movie.domain.model.BookedTicket
import woowacourse.movie.utils.Destination

class BookingCompletePresenter(
    private val bookingCompleteView: BookingCompleteContract.View,
) : BookingCompleteContract.Presenter {
    private lateinit var bookedTicket: BookedTicket
    private lateinit var destination: Destination

    override fun loadBookedTicket(
        bookedTicket: BookedTicket,
        destination: Destination,
    ) {
        this.bookedTicket = bookedTicket
        this.destination = destination

        bookingCompleteView.showMovieTitle(bookedTicket.movieTitle)
        bookingCompleteView.showScreeningDateTime(bookedTicket.movieSchedule.screeningDateTime)
        bookingCompleteView.showDetailInfos(
            bookedTicket.headcount,
            bookedTicket.movieSchedule.seats,
            bookedTicket.theaterName,
        )
        bookingCompleteView.showTotalPrice(bookedTicket.totalPrice())
    }

    override fun navigateTo() {
        when (destination) {
            Destination.COMPLETE -> bookingCompleteView.moveTo(Destination.HOME)
            Destination.HISTORY -> bookingCompleteView.moveTo(Destination.HISTORY)
            else -> bookingCompleteView.moveTo(Destination.HOME)
        }
    }
}
