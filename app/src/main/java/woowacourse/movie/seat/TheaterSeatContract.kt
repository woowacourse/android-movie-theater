package woowacourse.movie.seat

import android.content.Intent
import woowacourse.movie.model.Cinema
import woowacourse.movie.model.movieInfo.Title
import woowacourse.movie.model.theater.Seat

interface TheaterSeatContract {
    interface View {
        fun updateSeatDisplay(seat: Seat)

        fun navigateToPurchase(ticketId: Int)

        fun showConfirmationDialog(
            title: String,
            message: String,
            positiveLabel: String,
            onPositiveButtonClicked: () -> Unit,
            negativeLabel: String,
            onNegativeButtonClicked: () -> Unit,
        )

        fun setSeatBackground(
            seatId: String,
            color: String,
        )

        fun makeNotify(
            movieStartTime: Long,
            cinema: Cinema,
            ticketId: Int,
        )

        fun navigateToNextPage(intent: Intent)

        fun showTitle(title: Title)

        fun showPrice(price: Int)
    }

    interface Presenter {
        fun toggleSeatSelection(seatId: String)

        fun showConfirmationDialog(
            title: String,
            message: String,
            positiveLabel: String,
            onPositiveButtonClicked: () -> Unit,
            negativeLabel: String,
            onNegativeButtonClicked: () -> Unit,
        )

        fun saveTicketToDatabase(
            movieStartTime: Long,
            cinema: Cinema,
        )
    }
}
