package woowacourse.movie.ui.seat.contract

import woowacourse.movie.domain.model.movie.Headcount
import woowacourse.movie.domain.model.theater.BookedTicket
import woowacourse.movie.domain.model.theater.Seat
import woowacourse.movie.domain.model.theater.Theater
import java.time.LocalDateTime

interface BookingSeatContract {
    interface Presenter {
        fun loadState(
            theater: Theater,
            headcount: Headcount,
            title: String,
            bookedDateTime: LocalDateTime,
        )

        fun refreshTotalPrice()

        fun refreshMovieTitle()

        fun selectSeat(seat: Seat)

        fun refreshConfirmButton()

        fun insertBookedTicket()

        fun completeBookingSeat()
    }

    interface View {
        fun setTotalPrice(totalPrice: Int)

        fun setMovieTitle(movieTitle: String)

        fun toggleSeat(
            seatPosition: Seat,
            isOccupied: Boolean,
        )

        fun setConfirmButton(isEnabled: Boolean)

        fun startBookingCompleteActivity(bookedTicket: BookedTicket)
    }
}
