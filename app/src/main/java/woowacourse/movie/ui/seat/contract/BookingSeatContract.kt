package woowacourse.movie.ui.seat.contract

import woowacourse.movie.domain.model.Headcount
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.Theater

interface BookingSeatContract {
    interface Presenter {
        fun loadHeadcount(): Headcount

        fun loadMovieTitle(): String

        fun loadTheater(theater: Theater)

        fun refreshTotalPrice()

        fun refreshMovieTitle()

        fun selectSeat(seatTag: String)

        fun refreshConfirmButton()

        fun completeBookingSeat()
    }

    interface View {
        fun getHeadcount(): Headcount?

        fun getMovieTitle(): String?

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
