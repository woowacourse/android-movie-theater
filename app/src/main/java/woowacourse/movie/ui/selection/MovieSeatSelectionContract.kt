package woowacourse.movie.ui.selection

import woowacourse.movie.ui.HandleError
import woowacourse.movie.ui.reservation.ReservationDetail
import java.time.LocalDateTime

interface MovieSeatSelectionContract {
    interface View : HandleError {
        fun showTheater(
            rowSize: Int,
            colSize: Int,
        )

        fun showSelectedSeat(index: Int)

        fun showUnSelectedSeat(index: Int)

        fun showReservationTotalAmount(amount: Int)

        fun updateSelectCompletion(isComplete: Boolean)

        fun showMovieTitle(title: String)

        fun navigateToCompleteScreen(ticketId: Long)

        fun setAlarm(
            reservationId: Long,
            reservedTime: LocalDateTime,
            movieTitle: String,
        )
    }

    interface Presenter {
        fun loadTheaterInfo(reservationDetail: ReservationDetail)

        fun updateSelectCompletion()

        fun selectSeat(
            row: Int,
            col: Int,
        )

        fun recoverSeatSelection(index: Int)

        fun completeReservation()

        fun handleError(throwable: Throwable)
    }
}
