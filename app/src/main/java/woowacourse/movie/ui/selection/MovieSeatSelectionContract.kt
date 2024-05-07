package woowacourse.movie.ui.selection

import woowacourse.movie.ui.HandleError

interface MovieSeatSelectionContract {
    interface View : HandleError {
        fun showMovieTitle(movieTitle: String)

        fun showTheater(
            rowSize: Int,
            colSize: Int,
        )

        fun showSelectedSeat(index: Int)

        fun showUnSelectedSeat(index: Int)

        fun showReservationTotalAmount(amount: Int)

        fun updateSelectCompletion(isComplete: Boolean)

        fun showSeatReservationConfirmation(userTicketId: Long)
    }

    interface Presenter {
        fun loadTheaterInfo(reservationId: Long)

        fun updateSelectCompletion()

        fun selectSeat(
            row: Int,
            col: Int,
        )

        fun recoverSeatSelection(index: Int)

        fun reservationSeat()
    }
}
