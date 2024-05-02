package woowacourse.movie.ui.selection

import woowacourse.movie.ui.HandleError

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
    }

    interface Presenter {
        fun loadTheaterInfo(ticketId: Long)

        fun updateSelectCompletion()

        fun selectSeat(
            row: Int,
            col: Int,
        )

        fun recoverSeatSelection(index: Int)
    }
}
