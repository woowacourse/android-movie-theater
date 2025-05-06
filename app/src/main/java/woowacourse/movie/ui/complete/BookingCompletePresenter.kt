package woowacourse.movie.ui.complete

import woowacourse.movie.domain.model.BookedTicket

class BookingCompletePresenter(
    private val bookingCompleteView: BookingCompleteContract.View,
) : BookingCompleteContract.Presenter {
    private lateinit var bookedTicket: BookedTicket

    override fun loadBookedTicket(bookedTicket: BookedTicket) {
        this.bookedTicket = bookedTicket
        bookingCompleteView.showMovieTitle(bookedTicket.movieTitle)
        bookingCompleteView.showScreeningDateTime(bookedTicket.movieSchedule.screeningDateTime)
        bookingCompleteView.showDetailInfos(
            bookedTicket.headcount,
            bookedTicket.movieSchedule.seats,
            bookedTicket.theaterName,
        )
        bookingCompleteView.showTotalPrice(bookedTicket.totalPrice())
    }
}
