package woowacourse.movie.contract.ticket

import woowacourse.movie.domain.reservation.Seat
import java.time.LocalDateTime

interface ReservationDetailContract {
    interface Presenter {
        fun presentCancelDescription()

        fun presentTitle()

        fun presentShowtime()

        fun presentCount()

        fun presentPrice()
    }

    interface View {
        fun setCancelDescription(minutes: Int)

        fun setMovieTitle(movieTitle: String)

        fun setShowtime(showtime: LocalDateTime)

        fun setCount(
            seats: Set<Seat>,
            cinemaName: String,
        )

        fun setPrice(price: Int)
    }
}
