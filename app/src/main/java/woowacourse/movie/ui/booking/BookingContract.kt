package woowacourse.movie.ui.booking

import woowacourse.movie.model.main.MovieUiModel
import woowacourse.movie.ticket.TicketCount

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
    }
}
