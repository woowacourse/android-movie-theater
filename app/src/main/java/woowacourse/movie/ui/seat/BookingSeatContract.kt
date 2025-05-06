package woowacourse.movie.ui.seat

import woowacourse.movie.domain.model.Headcount
import woowacourse.movie.domain.model.MovieSchedule
import woowacourse.movie.domain.model.Seat

interface BookingSeatContract {
    interface Presenter {
        fun loadBookingSeatInfo(
            movieId: Long,
            movieSchedule: MovieSchedule,
            headcount: Headcount,
            theaterName: String,
        )

        fun updateSeat(seatTag: String)

        fun updateConfirmButton()

        fun loadBookedTicket()
    }

    interface View {
        fun showMovieTitle(movieTitle: String)

        fun showTotalPrice(totalPrice: Int)

        fun showSeatView(
            seatPosition: Seat,
            isOccupied: Boolean,
        )

        fun showConfirmButton(isEnabled: Boolean)

        fun moveToBookedTicket(
            theaterName: String,
            movieTitle: String,
            schedule: MovieSchedule,
            headcount: Headcount,
        )
    }
}
