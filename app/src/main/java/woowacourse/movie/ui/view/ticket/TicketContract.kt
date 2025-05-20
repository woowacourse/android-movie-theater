package woowacourse.movie.ui.view.ticket

import woowacourse.movie.domain.reservation.Seat
import java.time.LocalDateTime

interface TicketContract {
    interface Presenter

    interface View {
        fun setCancelDescription(minutes: Int)

        fun setMovieTitle(movieTitle: String)

        fun setShowtime(showtime: LocalDateTime)

        fun setCount(
            count: Int,
            seats: Set<Seat>,
            cinemaName: String,
        )

        fun setPrice(price: Int)
    }
}
