package woowacourse.movie.presentation.reservation.seat

import woowacourse.movie.presentation.uimodel.MovieTicketUiModel

interface SeatSelectionContract {
    interface View {
        fun showSeatingChart(
            rowCount: Int,
            colCount: Int,
            seatRankInfo: List<IntRange>,
        )

        fun changeSeatColor(
            row: Int,
            col: Int,
        )

        fun updateTotalPrice(price: Int)

        fun changeConfirmClickable(hasMatchedCount: Boolean)

        fun showAlreadyFilledSeatsSelectionMessage()

        fun moveToReservationResult(movieTicketUiModel: MovieTicketUiModel)
    }

    interface Presenter {
        fun attachView(view: View)

        fun detachView()

        fun onViewSetUp()

        fun loadSeatingChart()

        fun selectSeat(
            row: Int,
            col: Int,
        )

        fun onAcceptButtonClicked()
    }
}
