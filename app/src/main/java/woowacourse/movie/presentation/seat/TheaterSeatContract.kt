package woowacourse.movie.presentation.seat

import android.content.Intent
import woowacourse.movie.model.Reservation
import woowacourse.movie.model.movieInfo.Title
import woowacourse.movie.model.theater.Seat

interface TheaterSeatContract {
    interface View {
        fun updateSeatDisplay(seat: Seat)

        fun showConfirmationDialog()

        fun setSeatBackground(
            seatId: String,
            color: String,
        )

        fun navigateToPurchaseConfirmView(reservation: Reservation)

        fun showTitle(title: Title)

        fun showPrice(price: Int)

        fun showError()
    }

    interface Presenter {
        fun toggleSeatSelection(seatId: String)


        fun completeSeatSelection()

        fun confirmPurchase()

    }
}
