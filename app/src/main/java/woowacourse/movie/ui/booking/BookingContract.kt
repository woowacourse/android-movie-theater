package woowacourse.movie.ui.booking

import woowacourse.movie.model.BookedMovie
import woowacourse.movie.model.main.MovieUiModel
import woowacourse.movie.ticket.TicketCount
import java.time.LocalDateTime

interface BookingContract {

    interface View {
        fun setTicketCountText(count: Int)
    }

    interface Presenter {
        val movie: MovieUiModel
        var ticketCount: TicketCount

        fun initTicketCount()
        fun minusTicketCount()
        fun plusTicketCount()
        fun createBookedMovie(dateTime: LocalDateTime): BookedMovie
    }
}
