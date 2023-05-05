package woowacourse.movie.ui.booking

import woowacourse.movie.model.BookedMovie
import woowacourse.movie.model.main.MovieUiModel
import woowacourse.movie.ticket.TicketCount
import java.time.LocalDateTime

class BookingPresenter(
    private val bookingView: BookingContract.View,
    override val movie: MovieUiModel
) : BookingContract.Presenter {

    override var ticketCount: TicketCount = TicketCount()

    override fun initTicketCount() {
        bookingView.setTicketCountText(ticketCount.value)
    }

    override fun minusTicketCount() {
        ticketCount = ticketCount.minus()

        bookingView.setTicketCountText(ticketCount.value)
    }

    override fun plusTicketCount() {
        ticketCount = ticketCount.plus()

        bookingView.setTicketCountText(ticketCount.value)
    }

    override fun createBookedMovie(dateTime: LocalDateTime): BookedMovie {

        return BookedMovie(
            movieId = movie.id,
            theaterId = 0,
            ticketCount = ticketCount.value,
            bookedDateTime = dateTime
        )
    }
}
