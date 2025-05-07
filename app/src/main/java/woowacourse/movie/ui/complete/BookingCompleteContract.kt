package woowacourse.movie.ui.complete

import woowacourse.movie.domain.model.BookedTicket
import woowacourse.movie.domain.model.Headcount
import woowacourse.movie.domain.model.Seats
import java.time.LocalDateTime

interface BookingCompleteContract {
    interface Presenter {
        fun loadBookedTicket(bookedTicket: BookedTicket)
    }

    interface View {
        fun showMovieTitle(movieTitle: String)

        fun showScreeningDateTime(dateTime: LocalDateTime)

        fun showDetailInfos(
            headcount: Headcount,
            seats: Seats,
            theaterName: String,
        )

        fun showTotalPrice(totalPrice: Int)
    }
}
