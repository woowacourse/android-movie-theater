package woowacourse.movie.ui.seat.contract

import woowacourse.movie.domain.model.movie.Headcount
import woowacourse.movie.domain.model.theater.Seat
import woowacourse.movie.domain.model.theater.Seats
import woowacourse.movie.domain.model.theater.Theater

interface BookingSeatContract {
    interface Presenter {
        fun loadState(
            theater: Theater,
            headcount: Headcount,
            title: String
        )

        fun refreshTotalPrice()

        fun refreshMovieTitle()

        fun selectSeat(seat: Seat)

        fun refreshConfirmButton()

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

        fun startBookingCompleteActivity(
            movieTitle: String,
            headcount: Headcount,
            seats: Seats,
            theater: Theater,
        )
    }
}
