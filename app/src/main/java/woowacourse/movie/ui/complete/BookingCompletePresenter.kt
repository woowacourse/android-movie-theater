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
        // bookingCompleteView.handlePermission(bookedTicket) , 퍼미션 위치 더 고민해보기 + 현재 예매 내역에서 진입하는 상황도 존재
    }
}
